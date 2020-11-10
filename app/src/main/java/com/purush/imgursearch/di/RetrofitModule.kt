package com.purush.imgursearch.di

import com.purush.imgursearch.data.ImgurApi
import com.purush.imgursearch.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {

    @Provides
    fun getImgurApiService(retrofit: Retrofit): ImgurApi {
        return retrofit.create(ImgurApi::class.java)
    }

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun getOkHttpClient(): OkHttpClient {
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

}