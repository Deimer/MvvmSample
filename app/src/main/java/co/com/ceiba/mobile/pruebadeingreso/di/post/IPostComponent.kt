package co.com.ceiba.mobile.pruebadeingreso.di.post

import co.com.ceiba.mobile.pruebadeingreso.data.interactors.PostInteractor
import co.com.ceiba.mobile.pruebadeingreso.data.repositories.posts.PostRepository
import co.com.ceiba.mobile.pruebadeingreso.views.post.PostViewModel
import dagger.Component

@Component(modules = [PostModule::class])
interface IPostComponent {

    fun inject(postRepository: PostRepository)
    fun inject(postInteractor: PostInteractor)
    fun inject(postViewModel: PostViewModel)
}