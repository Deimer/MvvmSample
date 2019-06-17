package co.com.ceiba.mobile.pruebadeingreso.data.interactors

import co.com.ceiba.mobile.pruebadeingreso.data.dto.UserDTO
import co.com.ceiba.mobile.pruebadeingreso.data.entities.UserEntity
import co.com.ceiba.mobile.pruebadeingreso.data.models.UserModel
import co.com.ceiba.mobile.pruebadeingreso.data.repositories.users.IUserRepository
import co.com.ceiba.mobile.pruebadeingreso.di.user.DaggerIUserComponent
import co.com.ceiba.mobile.pruebadeingreso.di.user.UserModule
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserInteractor {

    @Inject
    lateinit var userRepository: IUserRepository

    init {
        DaggerIUserComponent.builder().userModule(UserModule()).build().inject(this)
    }

    fun getUsers(): Observable<List<UserModel>>? {
        val users = userRepository.getUsersDatabase()
        return if(users?.isNotEmpty()!!) {
            Observable.just(convertUsersEntityToModels(users))
        } else {
            userRepository.getUsers()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnNext { dtoList ->
                    userRepository.saveUsers(convertUsersDtoToEntity(dtoList))
                }
                ?.flatMap {
                    Observable.just(convertUsersEntityToModels(userRepository.getUsersDatabase()))
                }
        }
    }

    private fun convertUsersDtoToEntity(dtoList: List<UserDTO>): List<UserEntity> {
        val entities = mutableListOf<UserEntity>()
        dtoList.forEach { dto ->
            val entity = UserEntity().apply {
                id = dto.id
                name = dto.name
                email = dto.email
                phone = dto.phone
            }
            entities.add(entity)
        }
        return entities
    }

    private fun convertUsersEntityToModels(dtoList: List<UserEntity>?): List<UserModel> {
        val models = mutableListOf<UserModel>()
        dtoList?.forEach { dto ->
            val model = UserModel().apply {
                id = dto.id
                name = dto.name
                email = dto.email
                phone = dto.phone
            }
            models.add(model)
        }
        return models
    }
}