package co.com.ceiba.mobile.pruebadeingreso.di.post

import co.com.ceiba.mobile.pruebadeingreso.data.interactors.PostInteractor
import co.com.ceiba.mobile.pruebadeingreso.data.repositories.posts.IPostRepository
import co.com.ceiba.mobile.pruebadeingreso.data.repositories.posts.PostRepository
import dagger.Module
import dagger.Provides

@Module
class PostModule {

    @Provides
    fun providePostRepository(): IPostRepository {
        return PostRepository()
    }
    @Provides
    fun provideUserInteractor(): PostInteractor {
        return PostInteractor()
    }
}