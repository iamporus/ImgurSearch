package com.purush.imgursearch.utils

import androidx.fragment.app.Fragment
import com.purush.imgursearch.ImgurSearchApplication
import com.purush.imgursearch.ui.main.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {

    val imageRepository =
        (requireContext().applicationContext as ImgurSearchApplication).imageRepository
    val commentRepository =
        (requireContext().applicationContext as ImgurSearchApplication).commentRepository

    return ViewModelFactory(imageRepository, commentRepository)
}