package com.purush.imgursearch.data.source.remote.models

import com.purush.imgursearch.data.source.remote.schema.Image

data class ImageList(
    val images: List<Image>,
    val previousOffset: Int
)