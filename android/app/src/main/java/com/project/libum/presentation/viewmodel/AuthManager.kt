package com.project.libum.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class AuthManager(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        private const val EMAIL_KEY = "EMAIL"
        private const val PASSWORD_KEY = "PASSWORD"
    }

    var email: String? = savedStateHandle[EMAIL_KEY]
    var password: String? = savedStateHandle[PASSWORD_KEY]

    fun saveEmailAndPassword(email: String, password: String) {
        Log.d("AuthorizationActivity", "saveEmailAndPassword: $email -- $password")
        savedStateHandle[EMAIL_KEY] = email
        savedStateHandle[PASSWORD_KEY] = password
    }

    fun restoreState() {
        Log.d("AuthorizationActivity", "restoreState: $email -- $password")
        email = savedStateHandle[EMAIL_KEY]
        password = savedStateHandle[PASSWORD_KEY]
    }

}