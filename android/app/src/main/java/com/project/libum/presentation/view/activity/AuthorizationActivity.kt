package com.project.libum.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaClient
import com.google.android.recaptcha.RecaptchaException
import com.project.libum.BuildConfig
import com.project.libum.R
import com.project.libum.data.model.LoginRequest
import com.project.libum.databinding.ActivityAuthorizationBinding
import com.project.libum.domain.validation.EmailValidation
import com.project.libum.presentation.viewmodel.AuthorizationActivityModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

    private val authViewModel: AuthorizationActivityModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity()

        binding.emailFiled.emailField.setText(savedInstanceState?.getString(EMAIL_FILED, ""))
        binding.passwordFiled.passwordFiled.setText(savedInstanceState?.getString(PASSWORD_FIELD, ""))

        authViewModel.initializeCaptcha()
        authViewModel.loginResult.observe(this) { result ->
            result.onFailure {

            }.onSuccess {
                transactionToMain()
            }
            Log.d("Login Result", "$result")
        }

        binding.authorizationButton.setOnClickListener {
            clickAuthorizationButton()
        }

        binding.emailFiled.emailField.addTextChangedListener {
            setAuthFieldError()
            setClickableAuthButton()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EMAIL_FILED, binding.emailFiled.emailField.text.toString())
        outState.putString(PASSWORD_FIELD, binding.passwordFiled.passwordFiled.text.toString())
    }

    private fun clickAuthorizationButton(){
        Log.d("AuthorizationActivity", "onCreate: AuthorizationButton button clicked")
        authViewModel.executeCaptchaAndLogin (actionOnCorrectCaptcha  = {
            if(validateAuthFormData()){
                authViewModel.login(LoginRequest(
                    binding.emailFiled.emailField.text.toString(),
                    binding.passwordFiled.passwordFiled.text.toString()
                ))
            }
        })
    }

    private fun setClickableAuthButton(){
        binding.authorizationButton.isClickable = validateAuthFormData()
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

    private fun initializeActivity() {
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        supportActionBar?.hide();
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setContentView(binding.root)
    }

    companion object{
        const val EMAIL_FILED = "EMAIL_FIELD"
        const val PASSWORD_FIELD = "PASSWORD_FIELD"
    }
}