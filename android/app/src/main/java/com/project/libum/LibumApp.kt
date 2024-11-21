package com.project.libum

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LibumApp : Application(){

    override fun onCreate() {
        super.onCreate()
    }

}