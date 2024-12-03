package com.project.libum.domain.repository

import com.project.libum.data.local.dao.UserEntity
import com.project.libum.data.model.LoginRequest

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<UserEntity>
    suspend fun loginCached(): Result<UserEntity>
    suspend fun logout()
}