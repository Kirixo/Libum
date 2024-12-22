package com.project.libum.data.local.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey val id: Int,
    var encoding: String = "UTF-8",
    var readPercent: Int = 0,
    var lastReadPage: Int = 0
)