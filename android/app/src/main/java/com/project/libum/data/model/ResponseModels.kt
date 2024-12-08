package com.project.libum.data.model

data class LoginResponse(
    val email: String,
    val id: Int,
    val login: String
)

data class BookListResponse(
    val id: Int
)