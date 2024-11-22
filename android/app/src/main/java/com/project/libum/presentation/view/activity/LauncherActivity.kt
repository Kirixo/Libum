package com.project.libum.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Build
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.project.libum.LibumApp
import com.project.libum.core.exeption.IncorrectPasswordException
import com.project.libum.core.exeption.NoCachedUserException
import com.project.libum.domain.usecase.CheckAuthorizationUseCase
import kotlinx.coroutines.launch

class LauncherActivity : AppCompatActivity() {

    private val checkAuthorizationUseCase = CheckAuthorizationUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { isDataLoading }

            lifecycleScope.launch {
                val intent = checkAuthorizationUseCase.invoke(this@LauncherActivity)
                isDataLoading = false
                navigateToNextActivity(intent)
            }
        } else {
            navigateToSplashActivity()
        }
    }

    private var isDataLoading: Boolean = true

    private fun navigateToNextActivity(nextIntent: Intent) {
        startActivity(nextIntent)
        finish()
    }

    private fun navigateToSplashActivity() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }
}
