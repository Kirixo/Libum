package com.project.libum.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.URL

@Parcelize
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    var isFavorite: Boolean,
    var percentRead: Int?,
    var coverURL: URL?,
    var localPercentRead: Int? = 0,
    var lastReadPage: Int? = 0
) : Parcelable
