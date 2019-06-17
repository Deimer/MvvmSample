package co.com.ceiba.mobile.pruebadeingreso

import android.app.Application
import android.content.Context
import co.com.ceiba.mobile.pruebadeingreso.util.RUtil.Companion.R_string
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        val locale = Locale("es","ES")
        Locale.setDefault(locale)
        setupRealmConfiguration()
    }

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance(): MyApplication {
            return instance
        }

        fun getAppContext(): Context {
            return instance.applicationContext
        }
    }

    private fun setupRealmConfiguration() {
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .name(R_string(R.string.app_name))
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}