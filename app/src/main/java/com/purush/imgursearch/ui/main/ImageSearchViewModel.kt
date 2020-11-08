package com.purush.imgursearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.purush.imgursearch.data.repositories.ImageRepository
import com.purush.imgursearch.data.repositories.ImageSearchResult
import com.purush.imgursearch.data.schema.Image
import kotlinx.coroutines.*

@Suppress("unused")

private const val TAG = "SearchImagesViewModel"

class ImageSearchViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _imageList = MutableLiveData<List<Image>>() //backing property
    @Suppress("unused")
    val imageList: LiveData<List<Image>>
        get() = _imageList

    //TODO: Introduce SingleLiveEvent pattern
    private val _loadFailureEvent = MutableLiveData<Boolean>()  //backing property
    @Suppress("unused")
    val loadFailureEvent: LiveData<Boolean>
        get() = _loadFailureEvent

    @Suppress("unused")
    fun onQueryTextChanged(query: String) {

        coroutineScope.launch {

            withContext(Dispatchers.IO)
            {
                val data = imageRepository.getImagesByQuery(query)

                if (data is ImageSearchResult.Failure) {
                    _loadFailureEvent.postValue(true)
                } else {
                    data as ImageSearchResult.Success
                    _imageList.postValue(data.images?.images)
                    _loadFailureEvent.postValue(false)
                }

            }
        }
    }

}