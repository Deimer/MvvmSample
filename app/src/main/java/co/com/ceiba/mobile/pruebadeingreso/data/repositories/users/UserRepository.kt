package co.com.ceiba.mobile.pruebadeingreso.data.repositories.users

import co.com.ceiba.mobile.pruebadeingreso.data.dto.UserDTO
import co.com.ceiba.mobile.pruebadeingreso.data.entities.UserEntity
import co.com.ceiba.mobile.pruebadeingreso.di.user.DaggerIUserComponent
import co.com.ceiba.mobile.pruebadeingreso.di.user.UserModule
import co.com.ceiba.mobile.pruebadeingreso.network.ApiFactory
import io.reactivex.Observable
import io.realm.Realm

class UserRepository: IUserRepository {

    init {
        DaggerIUserComponent.builder().userModule(UserModule()).build().inject(this)
    }

    override fun getUsers(): Observable<List<UserDTO>>? {
        return ApiFactory.build()?.getUsers()
    }

    override fun getUsersDatabase(): List<UserEntity>? {
        var entities = mutableListOf<UserEntity>()
        try {
            val realmInstance = Realm.getDefaultInstance()
            entities = realmInstance.where(UserEntity::class.java).findAll()
        } catch (e: Exception) {
            println("UserInteractor(get) Error: " + e.message)
        }
        return entities
    }

    override fun saveUsers(entities: List<UserEntity>) {
        try {
            val realmInstance = Realm.getDefaultInstance()
            realmInstance.executeTransaction { realm ->
                realm.insertOrUpdate(entities)
            }
        } catch (e: Exception) {
            println("UserInteractor(save) Error: " + e.message)
        }
    }
}