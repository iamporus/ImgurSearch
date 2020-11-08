package com.purush.imgursearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.purush.imgursearch.ui.main.ImageSearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ImageSearchFragment())
                .commitNow()
        }
    }
}