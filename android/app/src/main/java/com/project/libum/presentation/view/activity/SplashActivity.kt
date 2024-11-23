package com.project.libum.presentation.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.project.libum.LibumApp
import com.project.libum.domain.usecase.LogInCachedUserUseCase
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
    lateinit var logInCachedUserUseCase: LogInCachedUserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val result = logInCachedUserUseCase.invoke()

            when (result) {
                is LoginResult.Success -> navigateToMainActivity(applicationContext)
                is LoginResult.NoCachedUser -> navigateToAuthorization(applicationContext,"No cached user found.")
                is LoginResult.IncorrectPasswordOrEmail -> navigateToAuthorization(applicationContext,"Incorrect password or email.")
                is LoginResult.NetworkError -> navigateToAuthorization(applicationContext, result.message)
            }
        }
    }



}
