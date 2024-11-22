package com.project.libum.data.local.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int,
    val email: String,
    val password: String,
    val login: String,
)