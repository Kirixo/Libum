package com.project.libum.presentation.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.project.libum.domain.usecase.LogInUseCase
import com.project.libum.domain.usecase.LoginResult
import com.project.libum.presentation.view.extension.navigateToAuthorization
import com.project.libum.presentation.view.extension.navigateToMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var logInUserUseCase: LogInUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val result = logInUserUseCase.invoke()

            when (result) {
                is LoginResult.Success -> navigateToMainActivity(applicationContext)
                is LoginResult.NoCachedUser -> navigateToAuthorization(applicationContext,"No cached user found.")
                is LoginResult.IncorrectPasswordOrEmail -> navigateToAuthorization(applicationContext,"Incorrect password or email.")
                is LoginResult.NetworkError -> navigateToAuthorization(applicationContext, result.message)
            }
        }
    }
}
