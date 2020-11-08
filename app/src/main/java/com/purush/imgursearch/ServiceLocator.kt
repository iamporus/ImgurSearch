package com.purush.imgursearch

import com.purush.imgursearch.data.ImgurApi
import com.purush.imgursearch.data.repositories.CommentRepository
import com.purush.imgursearch.data.repositories.DefaultCommentRepository
import com.purush.imgursearch.data.repositories.DefaultImageRepository
import com.purush.imgursearch.data.repositories.ImageRepository
import com.purush.imgursearch.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//TODO: Replace this with Dagger 2 injection
object ServiceLocator {

    var imageRepository: ImageRepository? = null
    var commentRepository: CommentRepository? = null

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkayHttpClient())
        .build()

    fun provideImageRepository(): ImageRepository {

        synchronized(this) {

            return imageRepository ?: imageRepository
            ?: DefaultImageRepository(getImgurApiService())
        }
    }

    fun provideCommentRepository(): CommentRepository {

        synchronized(this) {

            return commentRepository ?: commentRepository
            ?: DefaultCommentRepository(getImgurApiService())
        }
    }

    private fun getImgurApiService(): ImgurApi {
        return retrofit.create(ImgurApi::class.java)
    }

    class AuthTokenInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header(
                    Constants.AUTH_IDENTIFIER,
                    Constants.CLIENT_ID_IDENTIFIER + " " + Constants.CLIENT_ID
                )
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }

    private fun getOkayHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return if (Constants.INTERCEPT_RESPONSE) {
            OkHttpClient.Builder()
                .addInterceptor(AuthTokenInterceptor())
                .addInterceptor(interceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(AuthTokenInterceptor())
                .build()
        }
    }

}
