package com.purush.imgursearch.data.repositories

import android.util.Log
import com.purush.imgursearch.data.ImgurApi
import com.purush.imgursearch.data.models.ImageList
import com.purush.imgursearch.data.schema.Image
import kotlinx.coroutines.CancellationException

private const val TAG = "ImageRepository"

sealed class ImageSearchResult {

    data class Success(val images: ImageList?) : ImageSearchResult()
    object Failure : ImageSearchResult()
}

interface ImageRepository {

    suspend fun getImagesByQuery(searchQuery: String): ImageSearchResult
}

class DefaultImageRepository(
    private val imgurApi: ImgurApi,

    ) : ImageRepository {

    override suspend fun getImagesByQuery(searchQuery: String): ImageSearchResult {

        try {
            val response = imgurApi.getImagesByQuery(searchQuery)
            if (response.isSuccessful && response.body() != null) {

                var imageList: ImageList? = null
                val images = mutableListOf<Image>()
                response.body()?.let {
                    it.imageData.map { data ->
                        val title = data.title
                        if (data.imagesCount != 0) {
                            data.images.map { image ->
                                image.title = title
                                images.add(image)
                            }
                        }
                    }
                    imageList = ImageList(images, 0)
                }
                return ImageSearchResult.Success(imageList)
            } else {
                return ImageSearchResult.Failure
            }
        } catch (t: Throwable) {
            Log.e(TAG, "getImagesByQuery: $t")
            if (t !is CancellationException) {
                return ImageSearchResult.Failure
            } else {
                throw t
            }
        }
    }


}
