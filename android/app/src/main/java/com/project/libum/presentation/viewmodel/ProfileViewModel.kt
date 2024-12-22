package com.project.libum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libum.domain.repository.AuthRepository
import com.project.libum.domain.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject  constructor(
    private val authRepository: AuthRepository,
    private val userUseCases: UserUseCases
) : ViewModel() {

    fun logout(actionAfter: () -> Unit){
        viewModelScope.launch {
            authRepository.logout()
            actionAfter()
        }
    }

    fun getUserName(): String{
        var name = ""
        viewModelScope.launch {
            name = userUseCases.getUserData()?.username ?: "User not found :("
        }

        return name
    }

}