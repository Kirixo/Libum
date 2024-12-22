package com.project.libum.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.project.libum.LibumApp
import com.project.libum.R
import com.project.libum.data.model.LoginRequest
import com.project.libum.databinding.ActivityAuthorizationBinding
import com.project.libum.domain.usecase.LoginResult
import com.project.libum.domain.validation.EmailValidation
import com.project.libum.presentation.viewmodel.AuthorizationActivityModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

    private val authViewModel: AuthorizationActivityModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity()
        processErrorsFromExtras()
        authViewModel.initializeCaptcha()

        setClickableAuthButton()

        authViewModel.loginResult.observe(this) { result ->
            when (result) {
                is LoginResult.Success -> transactionToMain()
                else -> onErrorAction(result)
            }
        }

        binding.authorizationButton.setOnClickListener {
            login()
        }

        binding.emailFiled.emailField.addTextChangedListener {
            setAuthFieldError()
            setClickableAuthButton()
        }

        binding.emailFiled.emailField.setOnFocusChangeListener { view, focusValue ->
            if (focusValue){
                scrollToView(view)
            }
        }

        binding.passwordFiled.passwordFiled.setOnFocusChangeListener { view, focusValue ->
            if (focusValue){
                scrollToView(view)
            }
        }

        binding.passwordFiled.passwordVisibilityButton?.setOnClickListener {
            it.isActivated = !it.isActivated
            binding.passwordFiled.passwordFiled.inputType = if (it.isActivated) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            if(binding.passwordFiled.passwordFiled.isFocused){
                binding.passwordFiled.passwordFiled.setSelection(binding.passwordFiled.passwordFiled.text?.length ?: 0)
            }
        }

    }

    private fun onErrorAction(loginResult: LoginResult){
        val errorMsgId = when (loginResult) {
            is LoginResult.IncorrectPasswordOrEmail -> R.string.incorrect_password_error
            is LoginResult.NetworkError -> R.string.network_error
            else -> R.string.unknow_error
        }

        Toast.makeText(this, getString(errorMsgId), Toast.LENGTH_SHORT).show()
    }

    private fun login(){
        authViewModel.executeCaptcha (actionOnCorrectCaptcha  = {
            authViewModel.login(LoginRequest(
                binding.emailFiled.emailField.text.toString(),
                binding.passwordFiled.passwordFiled.text.toString()
            ))
        })
    }

    private fun setClickableAuthButton(){
        binding.authorizationButton.isClickable = validateAuthFormData()
        binding.authorizationButton.isEnabled = validateAuthFormData()
    }

    private fun setAuthFieldError(){
        if (validateAuthFormData()){
            hideEmailError()
        }else{
            showEmailError()
        }
    }

    private fun validateAuthFormData(): Boolean = EmailValidation.validate(binding.emailFiled.emailField.text.toString())

    private fun showEmailError(){
        binding.emailFiled.error.visibility = View.VISIBLE
        binding.emailFiled.error.text = getString(R.string.incorrect_email)
        binding.emailFiled.emailField.error = null
    }

    private fun hideEmailError(){
        binding.emailFiled.error.visibility = View.INVISIBLE
        binding.emailFiled.emailField.error = null
    }

    private fun transactionToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun scrollToView(view: View) {
        binding.scrollView.post {

            val scrollViewHeight = binding.scrollView.height
            val viewHeight = view.height

            val scrollTo = view.top - (scrollViewHeight - viewHeight) / 2 + 12

            binding.scrollView.smoothScrollTo(0, scrollTo)
        }
    }

    private fun processErrorsFromExtras() {
        val msg = intent.getStringExtra(LibumApp.ERROR_MSG)
        if (msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeActivity() {
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setContentView(binding.root)
    }


}