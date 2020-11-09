package com.purush.imgursearch.data.repositories

import com.purush.imgursearch.data.source.local.ImgurDao
import com.purush.imgursearch.data.source.local.entities.CommentEntity
import com.purush.imgursearch.data.source.local.entities.ImageEntity
import com.purush.imgursearch.data.source.local.entities.ImageWithComments
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

interface CommentRepository {

    suspend fun getCommentsForImage(imageId: String): ImageWithComments

    suspend fun insertImage(image: ImageEntity)

    suspend fun insertCommentToImage(image: ImageEntity, comment: CommentEntity)

    // converting suspend fun into Completable Future to be consumed from java
    fun getCommentsForImageAsync(imageId: String): CompletableFuture<ImageWithComments> {
        return GlobalScope.future {
            getCommentsForImage(imageId)
        }
    }

    // converting suspend fun into Completable Future to be consumed from java
    fun insertCommentToImageAsync(image: ImageEntity, comment: CommentEntity) {
        GlobalScope.future {
            insertImage(image)
            insertCommentToImage(image, comment)
        }
    }
}

/**
 * Default/concrete implementation of CommentRepository.
 * Should be replaced by mocks while testing
 */
class DefaultCommentRepository(
    private val imgurDao: ImgurDao
) : CommentRepository {

    override suspend fun getCommentsForImage(imageId: String): ImageWithComments {
        return imgurDao.getImageWithComments(imageId)
    }

    override suspend fun insertImage(image: ImageEntity) {
        imgurDao.insertImage(image)
    }

    override suspend fun insertCommentToImage(image: ImageEntity, comment: CommentEntity) {
        imgurDao.insertCommentToImage(image, comment)
    }

    /**
     * enables thread-safe singleton access
     */
    companion object {

        @Volatile
        private var instance: CommentRepository? = null

        fun getInstance(imageDao: ImgurDao): CommentRepository {
            return instance ?: instance ?: buildRepository(imageDao).also {
                instance = it
            }
        }

        private fun buildRepository(imageDao: ImgurDao): CommentRepository {
            return instance ?: instance ?: DefaultCommentRepository(imageDao)
        }

    }

}