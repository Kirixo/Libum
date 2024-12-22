package com.project.libum
import android.app.Application
import android.util.Log
import androidx.room.Room
import com.project.libum.data.local.FileStorageController
import com.project.libum.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LibumApp : Application(){

    lateinit var database: AppDatabase
        private set

    lateinit var bookDataBase: FileStorageController
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "app_database"
        ).build()

        bookDataBase = FileStorageController(applicationContext)
    }

    companion object{
        val ERROR_MSG = "ERROR_MSG"
    }

}