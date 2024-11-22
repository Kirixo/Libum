package com.project.libum.domain.recaptcha

import android.app.Application
import android.util.Log
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaClient
import com.google.android.recaptcha.RecaptchaException

// Data layer: RecaptchaServiceImpl.kt
class RecaptchaServiceImpl(
    private val application: Application,
    private val apiKey: String
) : RecaptchaService {

    private lateinit var recaptchaClient: RecaptchaClient

    override suspend fun initialize(): Result<Unit> {
        Log.d("Captcha", "Captcha start initializing")
        return try {
            recaptchaClient = Recaptcha.fetchClient(application, apiKey)
            Log.d("Captcha", "Captcha success initialized")
            Result.success(Unit)
        } catch (e: RecaptchaException) {
            Log.d("Captcha", "Error initialize: $e")
            Result.failure(e)
        }
    }

    override suspend fun execute(action: RecaptchaAction): Result<Unit> {
        Log.d("Captcha", "Captcha start executing")
        return try {
            recaptchaClient.execute(action)
            Log.d("Captcha", "Captcha success executed")
            Result.success(Unit)
        } catch (e: RecaptchaException) {
            Log.d("Captcha", "execute: $e")
            Result.failure(e)
        }
    }
}
