package com.purush.imgursearch.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.purush.imgursearch.data.source.local.ImgurDao
import com.purush.imgursearch.data.source.local.ImgurDatabase
import com.purush.imgursearch.data.source.local.entities.CommentEntity
import com.purush.imgursearch.data.source.local.entities.ImageEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ImgurDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var imgurDatabase: ImgurDatabase
    private lateinit var imgurDao: ImgurDao

    @Before
    fun setup() {
        imgurDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ImgurDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        imgurDao = imgurDatabase.imgurDao()
    }


    @Test
    fun insertCommentToImage() {

        val image = ImageEntity("id", "title")
        val comment = CommentEntity(1L, "comment", "id")
        imgurDao.insertImage(image)
        imgurDao.insertCommentToImage(image, comment)

        val imageWithComments = imgurDao.getImageWithComments("id").getOrAwaitValue()

        assertThat(imageWithComments.comments).contains(comment)
    }

    @After
    fun teardown() {
        imgurDatabase.close()
    }
}