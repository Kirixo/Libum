package com.project.libum.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.project.libum.R
import com.project.libum.data.model.LoginRequest
import com.project.libum.databinding.ActivityAuthorizationBinding
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

        binding.emailFiled.emailField.setText(savedInstanceState?.getString(EMAIL_FILED, ""))
        binding.passwordFiled.passwordFiled.setText(savedInstanceState?.getString(PASSWORD_FIELD, ""))

        setClickableAuthButton()
        authViewModel.initializeCaptcha()


        authViewModel.loginResult.observe(this) { result ->
            result.onFailure {
                authViewModel.processLoginError(result, actionOnIncorrectPassword = {
                    onIncorrectPasswordAction()
                })
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EMAIL_FILED, binding.emailFiled.emailField.text.toString())
        outState.putString(PASSWORD_FIELD, binding.passwordFiled.passwordFiled.text.toString())
    }

    private fun onIncorrectPasswordAction(){
        Toast.makeText(this, getString(R.string.incorrect_password), Toast.LENGTH_SHORT).show()
    }

    private fun clickAuthorizationButton(){
        Log.d("AuthorizationActivity", "onCreate: AuthorizationButton button clicked")
        authViewModel.executeCaptchaAndLogin (actionOnCorrectCaptcha  = {
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

    companion object{
        const val EMAIL_FILED = "EMAIL_FIELD"
        const val PASSWORD_FIELD = "PASSWORD_FIELD"
    }
}