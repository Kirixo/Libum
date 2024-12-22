package com.project.libum.domain.usecase

import android.util.Log
import com.project.libum.core.exeption.login.IncorrectPasswordException
import com.project.libum.core.exeption.login.NoCachedUserException
import com.project.libum.data.local.dao.UserEntity
import com.project.libum.data.model.LoginRequest
import com.project.libum.domain.repository.AuthRepository
import com.project.libum.domain.repository.UserCacheRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val cacheRepository: UserCacheRepository
) {

    suspend operator fun invoke(loginRequest: LoginRequest? = null): LoginResult {
        return try {
            val loginResult = loginRequest?.let {
                authRepository.login(it).also { result ->
                    result.getOrThrow().let { user -> cacheRepository.saveUserLoinData(user) }
                }
            } ?: authRepository.loginCached()
            return LoginResult.Success(loginResult.getOrNull()!!)
        } catch (e: Exception) {
            handleLoginException(e)
        }
    }

    private fun handleLoginException(e: Exception): LoginResult {
        return when (e) {
            is IncorrectPasswordException -> {
                Log.d("LogInUseCase", "handleLoginException: incorrect password ${e.message}")
                LoginResult.IncorrectPasswordOrEmail
            }
            is NoCachedUserException -> {
                Log.d("LogInUseCase", "handleLoginException: no cached user  ${e.message}")
                LoginResult.NoCachedUser
            }
            else -> {
                Log.d("LogInUseCase", "handleLoginException: xd ${e.message}")
                LoginResult.NetworkError(e.message ?: "Unknown error")
            }
        }
    }
}


sealed class LoginResult {
    data class Success(val userEntity: UserEntity) : LoginResult()
    data object NoCachedUser : LoginResult()
    data object IncorrectPasswordOrEmail : LoginResult()
    data class NetworkError(val message: String) : LoginResult()
}