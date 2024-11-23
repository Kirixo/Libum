package com.project.libum.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.recaptcha.RecaptchaAction
import com.project.libum.core.exeption.IncorrectPasswordException
import com.project.libum.data.local.dao.UserEntity
import com.project.libum.data.model.LoginRequest
import com.project.libum.data.model.LoginResponse
import com.project.libum.domain.usecase.ExecuteRecaptchaUseCase
import com.project.libum.domain.usecase.InitializeRecaptchaUseCase
import com.project.libum.domain.repository.AuthRepository
import com.project.libum.domain.repository.UserCacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationActivityModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val executeRecaptchaUseCase: ExecuteRecaptchaUseCase,
    private val initializeRecaptchaUseCase: InitializeRecaptchaUseCase,
    private val cacheRepository: UserCacheRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<UserEntity>>()
    val loginResult: LiveData<Result<UserEntity>> = _loginResult


    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginResult.value = authRepository.login(request)
            try{
                cacheRepository.saveUserLoinData(loginResult.value?.getOrNull()!!)
            }catch (e: Exception){
            }
        }
    }

    fun initializeCaptcha() {
        viewModelScope.launch {
            val result = initializeRecaptchaUseCase()
            if (result.isFailure) {
                Log.e("AuthorizationViewModel", "Captcha initialization failed: ${result.exceptionOrNull()}")
            }
        }
    }

    fun executeCaptchaAndLogin(actionOnCorrectCaptcha: () -> Unit) {
        viewModelScope.launch {
            val result = executeRecaptchaUseCase(RecaptchaAction.LOGIN)
            if (result.isSuccess) {
                actionOnCorrectCaptcha()
            } else {
                Log.e("AuthorizationViewModel", "Captcha execution failed: ${result.exceptionOrNull()}")
            }
        }
    }

    fun processLoginError(response: Result<UserEntity>, actionOnIncorrectPassword: () -> Unit) {
        response.onFailure { exception ->
            when (exception) {
                is IncorrectPasswordException -> {
                    actionOnIncorrectPassword()
                }
                else -> {
                    Log.e("Login Error", "Error: ${exception.message}")
                }
            }
        }
    }

}