package co.com.ceiba.mobile.pruebadeingreso.data.repositories.users

import co.com.ceiba.mobile.pruebadeingreso.data.dto.UserDTO
import co.com.ceiba.mobile.pruebadeingreso.data.entities.UserEntity
import io.reactivex.Observable

interface IUserRepository {

    fun getUsers(): Observable<List<UserDTO>>?

    fun getUsersDatabase(): List<UserEntity>?

    fun saveUsers(entities: List<UserEntity>)
}