package com.purush.imgursearch

import com.purush.imgursearch.data.ImgurApi
import com.purush.imgursearch.data.repositories.CommentRepository
import com.purush.imgursearch.data.repositories.DefaultCommentRepository
import com.purush.imgursearch.data.repositories.DefaultImageRepository
import com.purush.imgursearch.data.repositories.ImageRepository

object ServiceLocator {

    var imageRepository: ImageRepository? = null
    var commentRepository: CommentRepository? = null

    fun provideImageRepository(): ImageRepository {

        synchronized(this) {

            return imageRepository ?: imageRepository
            ?: DefaultImageRepository(getImgurApiService())
        }
    }

    private fun getImgurApiService(): ImgurApi {
        TODO("Not yet implemented")
    }

    fun provideCommentRepository(): CommentRepository {

        synchronized(this) {

            return commentRepository ?: commentRepository
            ?: DefaultCommentRepository(getImgurApiService())
        }
    }

}
