package co.com.ceiba.mobile.pruebadeingreso.data.repositories.posts

import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.dto.PostDTO
import co.com.ceiba.mobile.pruebadeingreso.data.entities.UserEntity
import co.com.ceiba.mobile.pruebadeingreso.network.ApiFactory
import co.com.ceiba.mobile.pruebadeingreso.util.RUtil
import io.reactivex.Observable
import io.realm.Realm

class PostRepository: IPostRepository {

    override fun getUser(userId: Int): Observable<UserEntity>? {
        var entity: UserEntity? = null
        try {
            val realmInstance = Realm.getDefaultInstance()
            entity = realmInstance.where(UserEntity::class.java).equalTo(RUtil.R_string(R.string.id), userId).findFirst()!!
        } catch (e: Exception) {
            println("UserInteractor(get) Error: " + e.message)
        }
        return Observable.just(entity)
    }

    override fun getPosts(userId: Int): Observable<List<PostDTO>>? {
        return ApiFactory.build()?.getPosts(userId)
    }
}