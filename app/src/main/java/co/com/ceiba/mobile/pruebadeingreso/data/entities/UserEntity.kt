package co.com.ceiba.mobile.pruebadeingreso.data.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserEntity: RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var name: String? = null
    var email: String? = null
    var phone: String? = null
}