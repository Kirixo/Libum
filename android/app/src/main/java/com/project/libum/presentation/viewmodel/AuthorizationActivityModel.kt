package com.project.libum.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libum.data.model.LoginRequest
import com.project.libum.data.model.LoginResponse
import com.project.libum.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationActivityModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginResult.value = authRepository.login(request)
        }
    }
}