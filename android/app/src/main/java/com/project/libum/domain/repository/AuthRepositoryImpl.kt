package com.project.libum.domain.repository

import com.project.libum.data.model.LoginRequest
import com.project.libum.data.model.LoginResponse
import com.project.libum.data.remote.ApiClient.apiService


class AuthRepositoryImpl: AuthRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = apiService.login(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}