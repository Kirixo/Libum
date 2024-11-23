package com.project.libum
import android.app.Application
import android.util.Log
import androidx.room.Room
import com.project.libum.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LibumApp : Application(){

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        Log.d("LibumApp", "onCreate: start")

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "app_database"
        ).build()

    }

    companion object{
        val ERROR_MSG = "ERROR_MSG"
    }

}