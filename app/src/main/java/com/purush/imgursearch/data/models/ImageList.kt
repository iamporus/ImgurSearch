package com.purush.imgursearch.data.models

import com.purush.imgursearch.data.schema.Image

data class ImageList(
    val images: List<Image>,
    val previousOffset: Int
)