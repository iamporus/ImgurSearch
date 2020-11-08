package com.purush.imgursearch.data

/**
 * Describes endpoints to fetch the images from Imgur
 *
 * Read the documentation [here](https://apidocs.imgur.com/)
 */
interface ImgurApi {

    //TODO convert to retrofit call
    suspend fun getImagesByQuery(searchQuery: String)

}
