package com.purush.imgursearch

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.purush.imgursearch.data.repositories.CommentRepository
import com.purush.imgursearch.data.repositories.ImageRepository

class ImgurSearchApplication : Application() {

    //TODO: Replace Service Locator with Dagger 2

    val imageRepository: ImageRepository
        get() = ServiceLocator.provideImageRepository()

    val commentRepository: CommentRepository
        get() = ServiceLocator.provideCommentRepository(applicationContext)

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        );
    }
}