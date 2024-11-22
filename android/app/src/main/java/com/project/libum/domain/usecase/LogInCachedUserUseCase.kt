package com.project.libum.domain.usecase

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

        if (userDao != null){

            val loginRequest = LoginRequest(
                email = userDao.email,
                password = userDao.password
            )

            val res = authRepository.login(loginRequest)

            if (res.isFailure){
                throw IncorrectPasswordException("Incorrect password")
            }

        }else{
            throw NoCachedUserException(null)
        }
    }

}