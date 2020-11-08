package com.purush.imgursearch.data.schema

import com.google.gson.annotations.SerializedName

data class ImgurResponseSchema(
    @SerializedName("data")
    val imageData: List<Data>,
    val status: Int,
    val success: Boolean
)