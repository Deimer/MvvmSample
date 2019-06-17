package co.com.ceiba.mobile.pruebadeingreso.data.repositories.posts

import co.com.ceiba.mobile.pruebadeingreso.data.dto.PostDTO
import co.com.ceiba.mobile.pruebadeingreso.data.entities.UserEntity
import io.reactivex.Observable

interface IPostRepository {

    fun getUser(userId: Int): Observable<UserEntity>?

    fun getPosts(userId: Int): Observable<List<PostDTO>>?
}