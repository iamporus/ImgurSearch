package com.purush.imgursearch.di

import android.content.Context
import com.purush.imgursearch.ui.main.ImageSearchFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ImageRepositoryModule::class,
        CommentRepositoryModule::class,
        RetrofitModule::class,
        ImgurDaoModule::class
    ]
)
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(imageSearchFragment: ImageSearchFragment)
}