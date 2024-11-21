package com.project.libum.domain.recaptcha

import android.app.Application
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
        return try {
            recaptchaClient = Recaptcha.fetchClient(application, apiKey)
            Result.success(Unit)
        } catch (e: RecaptchaException) {
            Result.failure(e)
        }
    }

    override suspend fun execute(action: RecaptchaAction): Result<Unit> {
        return try {
            recaptchaClient.execute(action)
            Result.success(Unit)
        } catch (e: RecaptchaException) {
            Result.failure(e)
        }
    }
}
