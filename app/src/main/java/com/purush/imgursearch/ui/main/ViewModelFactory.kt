package com.purush.imgursearch.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purush.imgursearch.data.repositories.CommentRepository
import com.purush.imgursearch.data.repositories.ImageRepository
import com.purush.imgursearch.ui.details.ImageDetailsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val imagesRepository: ImageRepository,
    @Suppress("unused") private val commentRepository: CommentRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass)
        {
            when {
                isAssignableFrom(ImageSearchViewModel::class.java) -> {
                    ImageSearchViewModel(imagesRepository)
                }
                isAssignableFrom(ImageDetailsViewModel::class.java) -> {
                    ImageDetailsViewModel(commentRepository)
                }
                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        } as T

    }

}