package com.project.libum.domain.repository

import android.util.Log
import com.project.libum.core.exeption.IncorrectPasswordException
import com.project.libum.data.local.dao.UserDao
import com.project.libum.data.local.dao.UserEntity
import com.project.libum.data.model.LoginRequest
import com.project.libum.data.model.LoginResponse
import com.project.libum.data.remote.ApiClient.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AuthRepositoryImpl(
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun login(
        request: LoginRequest
    ): Result<LoginResponse> {
        return try {
            val response = apiService.login(request)
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->

                    val userEntity = UserEntity(
                        id = loginResponse.id,
                        email = loginResponse.email,
                        login = loginResponse.login,
                        password = request.password
                    )

                    withContext(Dispatchers.IO) {
                        Log.d("Cached auth", "login: saved user ${loginResponse.email} ${request.password}")
                        userDao.insertUser(userEntity)
                    }

                    Result.success(loginResponse)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                when(response.code()){
                    401 -> Result.failure(IncorrectPasswordException("Incorrect password"))
                    404 -> Result.failure(IncorrectPasswordException("Network error"))
                    else -> Result.failure(Exception("Login failed"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}