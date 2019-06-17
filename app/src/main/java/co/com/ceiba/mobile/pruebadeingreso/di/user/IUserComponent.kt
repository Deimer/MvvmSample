package co.com.ceiba.mobile.pruebadeingreso.di.user

import co.com.ceiba.mobile.pruebadeingreso.data.interactors.UserInteractor
import co.com.ceiba.mobile.pruebadeingreso.data.repositories.users.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.views.main.MainViewModel
import dagger.Component

@Component(modules = [UserModule::class])
interface IUserComponent {

    fun inject(userRepository: UserRepository)
    fun inject(userInteractor: UserInteractor)
    fun inject(mainViewModel: MainViewModel)
}