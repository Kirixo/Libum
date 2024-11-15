package com.project.libum.domain.usecase

import com.google.android.recaptcha.RecaptchaAction
import com.project.libum.domain.recaptcha.RecaptchaService

class ExecuteRecaptchaUseCase(private val recaptchaService: RecaptchaService) {
    suspend operator fun invoke(action: RecaptchaAction): Result<Unit> {
        return recaptchaService.execute(action)
    }
}
