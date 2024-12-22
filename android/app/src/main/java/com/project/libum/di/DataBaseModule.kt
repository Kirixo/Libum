package com.project.libum.di

import android.content.Context
import androidx.room.Room
import com.project.libum.data.local.FileStorageController
import com.project.libum.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.project.libum.data.local.AppDatabase
import com.project.libum.data.local.dao.BookDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideBookDao(db: AppDatabase): BookDao {
        return db.bookDao()
    }

    @Provides
    @Singleton
    fun provideCacheData(context: Context): FileStorageController {
        return FileStorageController(context)
    }

}
