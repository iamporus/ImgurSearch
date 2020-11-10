package com.purush.imgursearch

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.purush.imgursearch.di.AppComponent
import com.purush.imgursearch.di.DaggerAppComponent

class ImgurSearchApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        );
    }
}