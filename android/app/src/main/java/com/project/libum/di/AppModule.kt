package com.project.libum.di

import android.app.Application
import com.project.libum.BuildConfig
import com.project.libum.data.remote.ApiService
import com.project.libum.domain.recaptcha.ExecuteRecaptchaUseCase
import com.project.libum.domain.recaptcha.InitializeRecaptchaUseCase
import com.project.libum.domain.recaptcha.RecaptchaService
import com.project.libum.domain.recaptcha.RecaptchaServiceImpl
import com.project.libum.domain.repository.AuthRepository
import com.project.libum.domain.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRecaptchaService(
        application: Application
    ): RecaptchaService {
        return RecaptchaServiceImpl(application, BuildConfig.CAPTCHA_API_KEY)
    }

    @Provides
    fun provideExecuteRecaptchaUseCase(
        recaptchaService: RecaptchaService
    ): ExecuteRecaptchaUseCase {
        return ExecuteRecaptchaUseCase(recaptchaService)
    }

    @Provides
    fun provideInitializeRecaptchaUseCase(
        recaptchaService: RecaptchaService
    ): InitializeRecaptchaUseCase {
        return InitializeRecaptchaUseCase(recaptchaService)
    }
}