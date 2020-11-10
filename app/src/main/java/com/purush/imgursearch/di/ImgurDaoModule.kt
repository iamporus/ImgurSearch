package com.purush.imgursearch.di

import android.content.Context
import com.purush.imgursearch.data.source.local.ImgurDao
import com.purush.imgursearch.data.source.local.ImgurDatabase
import dagger.Module
import dagger.Provides

@Module
class ImgurDaoModule {

    @Provides
    fun provideImgurDao(context: Context): ImgurDao {
        return ImgurDatabase.getInstance(context).imgurDao()
    }

}