package com.purush.imgursearch.data.repositories

import com.purush.imgursearch.data.ImgurApi

interface CommentRepository {

    suspend fun getCommentsForImage(imageId: String)
}

class DefaultCommentRepository(
    private val imgurApi: ImgurApi
) : CommentRepository {

    override suspend fun getCommentsForImage(imageId: String) {
        TODO("Not yet implemented")
    }

}