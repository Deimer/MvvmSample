package co.com.ceiba.mobile.pruebadeingreso.views.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.interactors.UserInteractor
import co.com.ceiba.mobile.pruebadeingreso.data.models.UserModel
import co.com.ceiba.mobile.pruebadeingreso.di.user.DaggerIUserComponent
import co.com.ceiba.mobile.pruebadeingreso.di.user.UserModule
import co.com.ceiba.mobile.pruebadeingreso.livedata.SingleLiveEvent
import co.com.ceiba.mobile.pruebadeingreso.util.RUtil.Companion.R_string
import javax.inject.Inject

@SuppressLint("CheckResult")
class MainViewModel: ViewModel() {

    @Inject
    lateinit var userInteractor: UserInteractor

    var singleLiveEvent: SingleLiveEvent<ViewEvent> = SingleLiveEvent()

    init {
        DaggerIUserComponent.builder().userModule(UserModule()).build().inject(this)
    }

    sealed class ViewEvent {
        class ResponseError (val errorMessage: String): ViewEvent()
        class ResponseSuccess(val users: List<UserModel>): ViewEvent()
    }

    fun getUsers() {
        userInteractor.getUsers()?.subscribe({
            if(it.isNotEmpty()) {
                singleLiveEvent.value = MainViewModel.ViewEvent.ResponseSuccess(it)
            } else {
                singleLiveEvent.value = MainViewModel.ViewEvent.ResponseError(R_string(R.string.list_is_empty))
            }
        }, {
            singleLiveEvent.value = MainViewModel.ViewEvent.ResponseError(it.message.toString())
        })
    }
}