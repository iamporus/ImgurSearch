package com.purush.imgursearch.data.source.local.entities

import androidx.room.*
import com.purush.imgursearch.data.source.local.DatabaseConstants.COMMENT_TABLE
import com.purush.imgursearch.data.source.local.DatabaseConstants.COMMENT_TABLE_COLUMN_COMMENT
import com.purush.imgursearch.data.source.local.DatabaseConstants.COMMENT_TABLE_COLUMN_ID
import com.purush.imgursearch.data.source.local.DatabaseConstants.COMMENT_TABLE_COLUMN_IMAGE_OWNER_ID
import com.purush.imgursearch.data.source.local.DatabaseConstants.IMAGE_TABLE
import com.purush.imgursearch.data.source.local.DatabaseConstants.IMAGE_TABLE_COLUMN_ID
import com.purush.imgursearch.data.source.local.DatabaseConstants.IMAGE_TABLE_COLUMN_TITLE

@Entity(tableName = IMAGE_TABLE)
data class ImageEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = IMAGE_TABLE_COLUMN_ID)
    val id: String,

    @ColumnInfo(name = IMAGE_TABLE_COLUMN_TITLE)
    val title: String
)

@Entity(tableName = COMMENT_TABLE)
data class CommentEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COMMENT_TABLE_COLUMN_ID)
    val commentId: Long,

    @ColumnInfo(name = COMMENT_TABLE_COLUMN_COMMENT)
    val comment: String,

    @ColumnInfo(name = COMMENT_TABLE_COLUMN_IMAGE_OWNER_ID)
    var imageOwnerId: String
) {
    constructor(comment: String, imageOwnerId: String) :
            this(0, comment, imageOwnerId)
}

// allows one to many relationship between image and comment tables
data class ImageWithComments(

    @Embedded val imageEntity: ImageEntity,
    @Relation(
        parentColumn = IMAGE_TABLE_COLUMN_ID,
        entityColumn = COMMENT_TABLE_COLUMN_IMAGE_OWNER_ID
    )

    val comments: List<CommentEntity>
)

