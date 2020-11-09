package com.purush.imgursearch.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.purush.imgursearch.data.source.local.entities.CommentEntity
import com.purush.imgursearch.data.source.local.entities.ImageEntity
import com.purush.imgursearch.data.source.local.entities.ImageWithComments

@Dao
abstract class ImgurDao {

    @Transaction
    @Query("SELECT * from image WHERE image_id = :imageId")
    abstract suspend fun getImageWithComments(imageId: String): ImageWithComments

    @Insert
    abstract suspend fun insertImage(image: ImageEntity)

    @Insert
    abstract suspend fun insertComment(comment: CommentEntity)

    suspend fun insertCommentToImage(
        image: ImageEntity,
        comment: CommentEntity
    ) {
        comment.imageOwnerId = image.id
        insertComment(comment)
    }
}