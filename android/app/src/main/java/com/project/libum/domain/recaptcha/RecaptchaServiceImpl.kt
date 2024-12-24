package com.project.libum.domain.recaptcha

import android.app.Application
import android.util.Log
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaClient
import com.google.android.recaptcha.RecaptchaException

class RecaptchaServiceImpl(
    private val application: Application,
    private val apiKey: String
) : RecaptchaService {

    private lateinit var recaptchaClient: RecaptchaClient

    override suspend fun initialize(): Result<Unit> {
        Log.d(TAG, "Captcha start initializing")
        try {
            recaptchaClient = Recaptcha.fetchClient(application, apiKey)
            Log.d(TAG, "Captcha success initialized")
            return  Result.success(Unit)
        } catch (e: RecaptchaException) {
            Log.d(TAG, "Error initialize: $e")
            return Result.failure(e)
        }
    }

    override suspend fun execute(action: RecaptchaAction): Result<Unit> {
        Log.d(TAG, "Captcha start executing")
        return try {
            recaptchaClient.execute(action)
            Log.d(TAG, "Captcha success executed")
            Result.success(Unit)
        } catch (e: RecaptchaException) {
            Log.d(TAG, "execute: $e")
            Result.failure(e)
        }
    }

    companion object{
        const val TAG = "CAPTCHA"
    }
}
