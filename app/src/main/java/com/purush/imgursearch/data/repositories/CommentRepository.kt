package com.purush.imgursearch.data.repositories

import androidx.lifecycle.LiveData
import com.purush.imgursearch.data.source.local.ImgurDao
import com.purush.imgursearch.data.source.local.ImgurDatabase
import com.purush.imgursearch.data.source.local.entities.CommentEntity
import com.purush.imgursearch.data.source.local.entities.ImageEntity
import com.purush.imgursearch.data.source.local.entities.ImageWithComments

interface CommentRepository {

    fun getCommentsForImage(imageId: String): LiveData<ImageWithComments>

    fun insertCommentToImage(image: ImageEntity, comment: CommentEntity)
}

/**
 * Default/concrete implementation of CommentRepository.
 * Should be replaced by mocks while testing
 */
class DefaultCommentRepository(
    private val imgurDao: ImgurDao
) : CommentRepository {

    override fun getCommentsForImage(imageId: String): LiveData<ImageWithComments> {
        return imgurDao.getImageWithComments(imageId)
    }

    override fun insertCommentToImage(image: ImageEntity, comment: CommentEntity) {
        ImgurDatabase.databaseWriteExecutor.execute {
            insertImage(image)
            imgurDao.insertCommentToImage(image, comment)
        }
    }

    private fun insertImage(image: ImageEntity) {
        imgurDao.insertImage(image)
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