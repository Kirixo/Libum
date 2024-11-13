package com.project.libum.domain.repository

import com.project.libum.data.model.LoginRequest
import com.project.libum.data.model.LoginResponse

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
}