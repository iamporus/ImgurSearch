package com.purush.imgursearch.di

import com.purush.imgursearch.data.repositories.DefaultImageRepository
import com.purush.imgursearch.data.repositories.ImageRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ImageRepositoryModule {

    @Binds abstract fun provideDefaultImageRepository( imageRepository: DefaultImageRepository): ImageRepository
}