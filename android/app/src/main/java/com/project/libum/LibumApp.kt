package com.project.libum
import androidx.room.Room
import android.app.Application
import com.project.libum.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LibumApp : Application(){
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "app_database"
        ).build()
    }

}