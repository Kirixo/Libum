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
import kotlinx.coroutines.launch

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var recaptchaClient: RecaptchaClient
    private lateinit var binding: ActivityAuthorizationBinding
    private val authViewModel: AuthorizationActivityModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity()
        initializeRecaptchaClient()

        authViewModel.loginResult.observe(this){ result ->
            result.onFailure {

            }.onSuccess {

            }
        }

        binding.authorizationButton.setOnClickListener {
            clickAuthorizationButton()
        }

        binding.emailFiled.emailFiled.addTextChangedListener { email ->
            if (EmailValidation.validate(email.toString())){
                showEmailError()
            }else{
                hideEmailError()
            }
        }
    }

    private fun clickAuthorizationButton(){
        Log.d("AuthorizationActivity", "onCreate: AuthorizationButton button clicked")
        if (::recaptchaClient.isInitialized) {
            executeLoginAction(actionOnCorrectCaptcha = {
                authViewModel.login(LoginRequest(
                    binding.emailFiled.emailFiled.toString(),
                    binding.passwordFiled.passwordFiled.toString()))
            })
        } else {
            Log.d("AuthorizationActivity", "Problem with captcha init")
        }
    }

    private fun showEmailError(){
        binding.emailFiled.error.visibility = View.VISIBLE
        binding.emailFiled.emailFiled.error = "Incorrect email"
    }

    private fun hideEmailError(){
        binding.emailFiled.error.visibility = View.INVISIBLE
    }

    private fun executeLoginAction(actionOnCorrectCaptcha: () -> Unit) {
        lifecycleScope.launch {
            recaptchaClient
                .execute(RecaptchaAction.LOGIN)
                .onSuccess { _ ->
                    actionOnCorrectCaptcha.invoke()
                    Toast.makeText(applicationContext, "Captcha is correct", Toast.LENGTH_SHORT).show()
                }
                .onFailure { exception ->
                    Log.e("AuthorizationActivity", "Problem with captcha init: ${exception.toString()}" )
                }
        }
    }

    private fun initializeRecaptchaClient() {
        lifecycleScope.launch {
            try {
                recaptchaClient = Recaptcha.fetchClient(application, BuildConfig.CAPTCHA_API_KEY)
                Log.d("AuthorizationActivity", "Captcha init success")
            } catch (e: RecaptchaException) {
                Log.e("AuthorizationActivity", e.toString())
            }
        }

    }

    private fun transactionToAuthorize(){
        val intent = Intent(this, AuthorizationActivity::class.java)
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
}