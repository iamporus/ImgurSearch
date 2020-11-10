package com.purush.imgursearch.di

import com.purush.imgursearch.data.ImgurApi
import dagger.Binds
import dagger.Module

@Module
abstract class ImgurApiModule {

    @Binds
    abstract fun provideImgurApi(imgurApi: ImgurApi): ImgurApi

}