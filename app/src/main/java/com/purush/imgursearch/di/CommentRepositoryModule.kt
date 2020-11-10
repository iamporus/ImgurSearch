package com.purush.imgursearch.di

import com.purush.imgursearch.data.repositories.CommentRepository
import com.purush.imgursearch.data.repositories.DefaultCommentRepository
import dagger.Binds
import dagger.Module

@Module
abstract class CommentRepositoryModule {

    @Binds
    abstract fun provideDefaultCommentRepository(commentRepository: DefaultCommentRepository): CommentRepository
}