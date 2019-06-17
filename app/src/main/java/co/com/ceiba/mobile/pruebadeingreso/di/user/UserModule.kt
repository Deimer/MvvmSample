package co.com.ceiba.mobile.pruebadeingreso.di.user

import co.com.ceiba.mobile.pruebadeingreso.data.interactors.UserInteractor
import co.com.ceiba.mobile.pruebadeingreso.data.repositories.users.IUserRepository
import co.com.ceiba.mobile.pruebadeingreso.data.repositories.users.UserRepository
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @Provides
    fun provideUserRepository(): IUserRepository {
        return UserRepository()
    }
    @Provides
    fun provideUserInteractor(): UserInteractor {
        return UserInteractor()
    }
}