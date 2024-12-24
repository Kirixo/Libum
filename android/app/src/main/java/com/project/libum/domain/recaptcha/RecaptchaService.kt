package com.project.libum.domain.recaptcha

import com.google.android.recaptcha.RecaptchaAction

interface RecaptchaService {
    suspend fun initialize(): Result<Unit>
    suspend fun execute(action: RecaptchaAction): Result<Unit>
}
