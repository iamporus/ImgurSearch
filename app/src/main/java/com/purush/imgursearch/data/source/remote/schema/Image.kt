package com.purush.imgursearch.data.source.remote.schema

import java.io.Serializable

data class Image(
    val id: String,
    val link: String,
    var title: String,
    ) : Serializable