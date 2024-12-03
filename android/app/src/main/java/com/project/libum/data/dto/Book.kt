package com.project.libum.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    var isFavorite: Boolean,
) : Parcelable
