package co.com.ceiba.mobile.pruebadeingreso.views.post

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.interactors.PostInteractor
import co.com.ceiba.mobile.pruebadeingreso.data.models.PostModel
import co.com.ceiba.mobile.pruebadeingreso.data.models.UserModel
import co.com.ceiba.mobile.pruebadeingreso.di.post.DaggerIPostComponent
import co.com.ceiba.mobile.pruebadeingreso.di.post.PostModule
import co.com.ceiba.mobile.pruebadeingreso.livedata.SingleLiveEvent
import co.com.ceiba.mobile.pruebadeingreso.util.RUtil.Companion.R_string
import javax.inject.Inject

@SuppressLint("CheckResult")
class PostViewModel: ViewModel() {

    @Inject
    lateinit var postInteractor: PostInteractor

    var singleLiveEvent: SingleLiveEvent<ViewEvent> = SingleLiveEvent()

    init {
        DaggerIPostComponent.builder().postModule(PostModule()).build().inject(this)
    }

    sealed class ViewEvent {
        class ResponseError (val errorMessage: String): ViewEvent()
        class ResponseSuccess(val posts: List<PostModel>): ViewEvent()
        class ResponseUser(val user: UserModel): ViewEvent()
    }

    fun getPosts(userId: Int) {
        postInteractor.getPosts(userId)?.subscribe({
            if(it.isNotEmpty()) {
                singleLiveEvent.value = PostViewModel.ViewEvent.ResponseSuccess(it)
            } else {
                singleLiveEvent.value = PostViewModel.ViewEvent.ResponseError(R_string(R.string.list_is_empty))
            }
        }, {
            singleLiveEvent.value = PostViewModel.ViewEvent.ResponseError(it.message.toString())
        })
    }

    fun getUser(userId: Int) {
        postInteractor.getUser(userId)?.subscribe { user ->
            if(user != null) {
                singleLiveEvent.value = PostViewModel.ViewEvent.ResponseUser(user)
            } else {
                singleLiveEvent.value = PostViewModel.ViewEvent.ResponseError(R_string(R.string.generic_error))
            }
        }
    }
}