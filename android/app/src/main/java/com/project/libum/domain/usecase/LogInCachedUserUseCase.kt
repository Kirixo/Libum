package com.project.libum.domain.usecase

import android.util.Log
import com.project.libum.core.exeption.IncorrectPasswordException
import com.project.libum.core.exeption.NoCachedUserException
import com.project.libum.domain.repository.AuthRepository
import javax.inject.Inject

class LogInCachedUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): LoginResult {
        return try {
            authRepository.loginCached()
            LoginResult.Success
        } catch (e: IncorrectPasswordException) {
            Log.d("LogInCachedUserUseCase", "invoke: ${e.message}")
            LoginResult.IncorrectPasswordOrEmail
        } catch (e: NoCachedUserException) {
            Log.d("LogInCachedUserUseCase", "invoke: ${e.message}")
            LoginResult.NoCachedUser
        } catch (e: Exception) {
            Log.d("LogInCachedUserUseCase", "invoke: ${e.message}")
            LoginResult.NetworkError(e.message ?: "Unknown error")
        }
    }
}

sealed class LoginResult {
    data object Success : LoginResult()
    data object NoCachedUser : LoginResult()
    data object IncorrectPasswordOrEmail : LoginResult()
    data class NetworkError(val message: String) : LoginResult()
}