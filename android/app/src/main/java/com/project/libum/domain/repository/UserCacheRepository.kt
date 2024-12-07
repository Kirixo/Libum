package com.project.libum.domain.repository

import com.project.libum.data.local.dao.UserEntity

interface UserCacheRepository {
    suspend fun saveUserLoinData(userEntity: UserEntity)
    suspend fun clearUserLoinData()
    suspend fun getUserData(): UserEntity
}