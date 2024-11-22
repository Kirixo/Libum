package com.project.libum
import androidx.room.Room
import android.app.Application
import android.content.Intent
import android.util.Log
import com.project.libum.core.exeption.IncorrectPasswordException
import com.project.libum.core.exeption.NoCachedUserException
import com.project.libum.data.local.AppDatabase
import com.project.libum.domain.usecase.LogInCachedUserUseCase
import com.project.libum.presentation.view.activity.AuthorizationActivity
import com.project.libum.presentation.view.activity.MainActivity
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class LibumApp : Application(){
    lateinit var database: AppDatabase
        private set

    @Inject
    lateinit var logInCachedUserUseCase: LogInCachedUserUseCase


    override fun onCreate() {
        super.onCreate()
        Log.d("LibumApp", "onCreate: start")

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "app_database"
        ).build()

        CoroutineScope(Dispatchers.Main).launch {
            val app = applicationContext as LibumApp
            var intent: Intent

            try {
                app.logInCachedUserUseCase.invoke()
                intent = Intent(applicationContext, MainActivity::class.java)
            } catch (e: IncorrectPasswordException) {
                Log.d("Cached auth", "SplashActivity: Failure - Incorrect Password")
                intent = Intent(applicationContext, AuthorizationActivity::class.java)
                intent.putExtra(LibumApp.ERROR_MSG, e.message)
            } catch (e: NoCachedUserException) {
                Log.d("Cached auth", "SplashActivity: Failure - No Cached User")
                intent = Intent(applicationContext, AuthorizationActivity::class.java)
            }

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    companion object{
        val ERROR_MSG = "ERROR_MSG"
    }

}