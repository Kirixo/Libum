package com.project.libum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.libum.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject  constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun logout(actionAfter: () -> Unit){
        viewModelScope.launch {
            authRepository.logout()
            actionAfter()
        }
    }
}