package com.project.libum.domain.usecase

import com.project.libum.domain.recaptcha.RecaptchaService

class InitializeRecaptchaUseCase(
    private val recaptchaService: RecaptchaService
) {
    suspend operator fun invoke(): Result<Unit> {
        return recaptchaService.initialize()
    }
}
