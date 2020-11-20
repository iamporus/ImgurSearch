package com.purush.imgursearch.data.source.remote.schema

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val id: String,
    val link: String,
    var title: String,
) : Parcelable