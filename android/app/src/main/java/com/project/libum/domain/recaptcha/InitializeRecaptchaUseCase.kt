package com.project.libum.domain.recaptcha

class InitializeRecaptchaUseCase(
    private val recaptchaService: RecaptchaService
) {
    suspend operator fun invoke(): Result<Unit> {
        return recaptchaService.initialize()
    }
}
