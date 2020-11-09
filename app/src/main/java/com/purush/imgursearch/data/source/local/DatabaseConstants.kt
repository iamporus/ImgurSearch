package com.purush.imgursearch.data.source.local

object DatabaseConstants {

    const val DATABASE_NAME = "imgur_search_db"

    const val IMAGE_TABLE = "image"
    const val COMMENT_TABLE = "comment"

    const val IMAGE_TABLE_COLUMN_ID = "image_id"
    const val IMAGE_TABLE_COLUMN_TITLE = "image_title"

    const val COMMENT_TABLE_COLUMN_ID = "comment_id"
    const val COMMENT_TABLE_COLUMN_COMMENT = "comment"
    const val COMMENT_TABLE_COLUMN_IMAGE_OWNER_ID = "image_owner_id"
}