package com.purush.imgursearch.data.repositories

import com.purush.imgursearch.data.ImgurApi

private const val TAG = "ImageRepository"

sealed class ImageSearchResult {

    //TODO: changes return type to proper data class
    data class Success(val images: Any) : ImageSearchResult()
    object Failure : ImageSearchResult()
}

interface ImageRepository {

    suspend fun getImagesByQuery(searchQuery: String): ImageSearchResult
}

class DefaultImageRepository(
    private val imgurApi: ImgurApi,

    ) : ImageRepository {
    override suspend fun getImagesByQuery(searchQuery: String): ImageSearchResult {
        TODO("Not yet implemented")
    }


}
