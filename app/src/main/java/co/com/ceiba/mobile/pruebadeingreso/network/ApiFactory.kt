package co.com.ceiba.mobile.pruebadeingreso.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiFactory {

    init {
        setup()
    }

    companion object {
        private const val defaultTimeOut: Long = 10
        private var BASE_URL = "https://jsonplaceholder.typicode.com"
        private var retrofit: Retrofit.Builder? = null

        fun build(timeOut: Long = defaultTimeOut): ApiService? {
            return setup(timeOut)
                    ?.build()?.create<ApiService>(ApiService::class.java)
        }

        private fun setup(timeOut: Long = defaultTimeOut): Retrofit.Builder? {
            retrofit = Retrofit.Builder()

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val clientBuilder = OkHttpClient.Builder()
                    .readTimeout(timeOut, TimeUnit.SECONDS)
                    .addInterceptor(logging)

            retrofit?.client(clientBuilder.build())
                    ?.baseUrl(BASE_URL)
                    ?.addConverterFactory(GsonConverterFactory.create())
                    ?.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            return retrofit
        }
    }

}