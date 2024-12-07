package com.project.libum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.libum.data.local.dao.UserDao
import com.project.libum.data.local.dao.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
