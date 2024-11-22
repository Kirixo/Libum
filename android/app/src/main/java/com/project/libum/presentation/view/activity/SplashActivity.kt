package com.project.libum.presentation.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.project.libum.LibumApp
import com.project.libum.core.exeption.IncorrectPasswordException
import com.project.libum.core.exeption.NoCachedUserException
import com.project.libum.domain.usecase.CheckAuthorizationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val checkAuthorizationUseCase = CheckAuthorizationUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val intent = checkAuthorizationUseCase.invoke(this@SplashActivity)
            startActivity(intent)
            finish()
        }
    }
}
