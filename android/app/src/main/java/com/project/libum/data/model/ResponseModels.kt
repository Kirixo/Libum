package com.project.libum.data.model

data class LoginResponse(
    val token: String,
    val userId: Int,
    val username: String
)