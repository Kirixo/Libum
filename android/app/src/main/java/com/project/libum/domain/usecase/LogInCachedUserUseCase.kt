package com.project.libum.domain.usecase

import android.util.Log
import com.project.libum.core.exeption.IncorrectPasswordException
import com.project.libum.core.exeption.NoCachedUserException
import com.project.libum.data.local.dao.UserDao
import com.project.libum.data.model.LoginRequest
import com.project.libum.domain.repository.AuthRepository

class LogInCachedUserUseCase(
    private val userDao: UserDao,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(){
        val userDao =  userDao.getUser()
        Log.d("Cached auth", "invoke: LogInCachedUserUseCase ${userDao?.password} ${userDao?.email}")
        if (userDao != null){

            val loginRequest = LoginRequest(
                email = userDao.email,
                password = userDao.password
            )

            val res = authRepository.login(loginRequest)

            if (res.isFailure){
                val exception = res.exceptionOrNull()
                if(exception != null){
                    throw exception
                }
            }
        }else{
            throw NoCachedUserException(null)
        }
    }
}