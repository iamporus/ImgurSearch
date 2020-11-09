package com.purush.imgursearch.data.source.remote.schema

import com.google.gson.annotations.SerializedName

data class Data(
    val images: List<Image>,
    @SerializedName("images_count")
    val imagesCount: Int,
    val title: String
)