package com.purush.imgursearch.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.purush.imgursearch.data.source.local.entities.CommentEntity
import com.purush.imgursearch.data.source.local.entities.ImageEntity
import com.purush.imgursearch.data.source.local.entities.ImageWithComments

/**
 * Dao for abstract access to ImgurDatabase
 */
@Dao
abstract class ImgurDao {

    @Transaction
    @Query("SELECT * from image WHERE image_id = :imageId")
    abstract fun getImageWithComments(imageId: String): LiveData<ImageWithComments>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertImage(image: ImageEntity)

    @Insert
    abstract fun insertComment(comment: CommentEntity): Long

    /**
     * work around for inserting into relations in Room
     */
     fun insertCommentToImage(
        image: ImageEntity,
        comment: CommentEntity
    ) {
        comment.imageOwnerId = image.id
        insertComment(comment)
    }
}