package com.project.libum.domain.usecase

import com.project.libum.data.local.dao.UserDao

class LogInCachedUserUseCase(
    private val userDao: UserDao,
) {
    suspend operator fun invoke(){
        userDao.getUser()
    }

}