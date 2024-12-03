package com.project.libum.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM user")
    suspend fun clearUserData()
}
