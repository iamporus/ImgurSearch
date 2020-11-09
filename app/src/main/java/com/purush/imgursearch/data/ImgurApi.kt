package com.purush.imgursearch.data

import com.purush.imgursearch.data.source.remote.schema.ImgurResponseSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Describes endpoints to fetch the images from Imgur
 *
 * Read the documentation [here](https://apidocs.imgur.com/)
 */
interface ImgurApi {

    @GET("/3/gallery/search/1")
    suspend fun getImagesByQuery(@Query("q") searchQuery: String): Response<ImgurResponseSchema>

}
