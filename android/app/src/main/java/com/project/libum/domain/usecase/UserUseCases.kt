package com.project.libum.domain.usecase

import com.project.libum.data.dto.User
import com.project.libum.data.local.dao.UserDao
import javax.inject.Inject

class UserUseCases @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun getUserData(): User? {
        val user = userDao.getUser()
            ?: return null
        return User(
            user.id,
            user.email
        )
    }

}