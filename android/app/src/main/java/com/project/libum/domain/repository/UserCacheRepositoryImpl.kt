package com.project.libum.domain.repository

import android.util.Log
import com.project.libum.core.exeption.NoCachedUserException
import com.project.libum.data.local.dao.UserDao
import com.project.libum.data.local.dao.UserEntity

class UserCacheRepositoryImpl(
    private val userDao: UserDao
): UserCacheRepository {

    override suspend fun saveUserLoinData(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    override suspend fun clearUserLoinData() {
        Log.d("UserCacheRepositoryImpl", "clearUserLoinData: ${userDao.getUser()?.password}")
        userDao.clearUserData()
    }

    override suspend fun getUserData(): UserEntity {
        val user = userDao.getUser() ?: throw NoCachedUserException(null)
        return user
    }
}