package co.com.ceiba.mobile.pruebadeingreso.network

import co.com.ceiba.mobile.pruebadeingreso.data.dto.PostDTO
import co.com.ceiba.mobile.pruebadeingreso.data.dto.UserDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/users")
    fun getUsers(): Observable<List<UserDTO>>

    @GET("/posts")
    fun getPosts(
        @Query("userId") userId: Int
    ): Observable<List<PostDTO>>
}