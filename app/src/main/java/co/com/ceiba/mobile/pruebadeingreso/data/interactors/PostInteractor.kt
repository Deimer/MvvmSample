package co.com.ceiba.mobile.pruebadeingreso.data.interactors

import co.com.ceiba.mobile.pruebadeingreso.data.dto.PostDTO
import co.com.ceiba.mobile.pruebadeingreso.data.entities.UserEntity
import co.com.ceiba.mobile.pruebadeingreso.data.models.PostModel
import co.com.ceiba.mobile.pruebadeingreso.data.models.UserModel
import co.com.ceiba.mobile.pruebadeingreso.data.repositories.posts.IPostRepository
import co.com.ceiba.mobile.pruebadeingreso.di.post.DaggerIPostComponent
import co.com.ceiba.mobile.pruebadeingreso.di.post.PostModule
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostInteractor {

    @Inject
    lateinit var postRepository: IPostRepository

    init {
        DaggerIPostComponent.builder().postModule(PostModule()).build().inject(this)
    }

    fun getUser(userId: Int): Observable<UserModel>? {
        return postRepository.getUser(userId)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap {
                Observable.just(convertUserDtoToEntity(it))
            }
    }

    private fun convertUserDtoToEntity(entity: UserEntity): UserModel? {
        return UserModel().apply {
            id = entity.id
            name = entity.name
            email = entity.email
            phone = entity.phone
        }
    }

    fun getPosts(userId: Int): Observable<List<PostModel>>? {
        return postRepository.getPosts(userId)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap { dtoList ->
                Observable.just(convertPostsDtoToModels(dtoList))
            }
    }

    private fun convertPostsDtoToModels(dtoList: List<PostDTO>): List<PostModel> {
        val models = mutableListOf<PostModel>()
        dtoList.forEach { dto ->
            val model = PostModel().apply {
                id = dto.id
                userId = dto.userId
                title = dto.title
                body = dto.body
            }
            models.add(model)
        }
        return models
    }
}