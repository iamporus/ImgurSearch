package com.purush.imgursearch.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.purush.imgursearch.data.source.local.DatabaseConstants.DATABASE_NAME
import com.purush.imgursearch.data.source.local.entities.CommentEntity
import com.purush.imgursearch.data.source.local.entities.ImageEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(
    entities =
    [
        ImageEntity::class,
        CommentEntity::class

    ], version = 1,
    exportSchema = false
)
abstract class ImgurDatabase : RoomDatabase() {

    abstract fun imgurDao(): ImgurDao

    companion object {

        @Volatile
        private var instance: ImgurDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getInstance(context: Context): ImgurDatabase {
            return instance ?: instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): ImgurDatabase {

            return Room.databaseBuilder(context, ImgurDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}