package com.purush.imgursearch.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.purush.imgursearch.data.repositories.ImageRepository
import com.purush.imgursearch.data.repositories.ImageSearchResult
import com.purush.imgursearch.data.schema.Image
import com.purush.imgursearch.utils.Event
import kotlinx.coroutines.*

@Suppress("unused")

private const val TAG = "SearchImagesViewModel"

class ImageSearchViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _imageList = MutableLiveData<List<Image>>() //backing property

    val imageList: LiveData<List<Image>>
        get() = _imageList

    private val _loadFailureEvent = MutableLiveData<Event<Boolean>>()  //backing property
    val loadFailureEvent: LiveData<Event<Boolean>>
        get() = _loadFailureEvent

    private val _loadSuccessEvent = MutableLiveData<Event<Boolean>>()  //backing property
    val loadSuccessEvent: LiveData<Event<Boolean>>
        get() = _loadSuccessEvent

    private var _previousQuery: String = ""

    fun onQueryTextChanged(query: String) {

        if (_previousQuery != query) {
            _previousQuery = query
            Log.d(TAG, "onQueryTextChanged: $query")
            coroutineScope.launch {

                withContext(Dispatchers.IO)
                {
                    val data = imageRepository.getImagesByQuery(query)

                    if (data is ImageSearchResult.Failure) {
                        _loadFailureEvent.postValue(Event(true))
                    } else {
                        data as ImageSearchResult.Success
                        _imageList.postValue(data.images?.images)
                        _loadSuccessEvent.postValue(Event(true))
                    }

                }
            }
        } else {
            _loadSuccessEvent.postValue(Event(true))
        }
    }

}