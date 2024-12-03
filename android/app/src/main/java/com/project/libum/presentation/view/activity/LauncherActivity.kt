package com.project.libum.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.project.libum.domain.usecase.LogInUseCase
import com.project.libum.domain.usecase.LoginResult
import com.project.libum.presentation.view.extension.navigateToAuthorization
import com.project.libum.presentation.view.extension.navigateToMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    @Inject
    lateinit var logInUserUseCase: LogInUseCase

    private var isDataLoading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { isDataLoading }

            lifecycleScope.launch {
                val result = logInUserUseCase.invoke()

                when (result) {
                    is LoginResult.Success -> navigateToMainActivity(applicationContext)
                    is LoginResult.NoCachedUser -> navigateToAuthorization(applicationContext,"No cached user found.")
                    is LoginResult.IncorrectPasswordOrEmail -> navigateToAuthorization(applicationContext,"Incorrect password or email.")
                    is LoginResult.NetworkError -> navigateToAuthorization(applicationContext,result.message)
                }
                Log.d("LauncherActivity", "onCreate: ${result.toString()}")
                isDataLoading = false
            }
        } else {
            navigateToSplashActivity()
        }
    }

    private fun navigateToSplashActivity() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }
}
