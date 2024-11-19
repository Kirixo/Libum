package com.project.libum.di

import android.app.Application
import android.content.Context
import com.project.libum.BuildConfig
import com.project.libum.data.local.dao.UserDao
import com.project.libum.domain.usecase.ExecuteRecaptchaUseCase
import com.project.libum.domain.usecase.InitializeRecaptchaUseCase
import com.project.libum.domain.recaptcha.RecaptchaService
import com.project.libum.domain.recaptcha.RecaptchaServiceImpl
import com.project.libum.domain.repository.AuthRepository
import com.project.libum.domain.repository.AuthRepositoryImpl
import com.project.libum.domain.repository.BooksListRepository
import com.project.libum.domain.repository.BooksListRepositoryImpl
import com.project.libum.domain.usecase.LogInCachedUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideAuthRepository(userDao: UserDao): AuthRepository {
        return AuthRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideBookListRepository(): BooksListRepository{
        return BooksListRepositoryImpl()
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

    @Provides
    fun provideLogInCachedUserUseCase(
        userDao: UserDao
    ): LogInCachedUserUseCase {
        return LogInCachedUserUseCase(userDao)
    }



}