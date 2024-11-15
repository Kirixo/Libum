package com.project.libum.domain.recaptcha

import com.google.android.recaptcha.RecaptchaAction

class ExecuteRecaptchaUseCase(private val recaptchaService: RecaptchaService) {
    suspend operator fun invoke(action: RecaptchaAction): Result<Unit> {
        return recaptchaService.execute(action)
    }
}
