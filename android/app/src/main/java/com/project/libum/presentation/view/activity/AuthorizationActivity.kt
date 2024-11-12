package com.project.libum.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaClient
import com.google.android.recaptcha.RecaptchaException
import com.project.libum.BuildConfig
import com.project.libum.R
import com.project.libum.databinding.ActivityAuthorizationBinding
import kotlinx.coroutines.launch

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var recaptchaClient: RecaptchaClient
    private lateinit var binding: ActivityAuthorizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity()
        initializeRecaptchaClient()

        binding.authorizationButton.setOnClickListener {
            Log.d("AuthorizationActivity", "onCreate: AuthorizationButton button clicked")
            if (::recaptchaClient.isInitialized) {
                executeLoginAction()
            } else {
                Log.d("AuthorizationActivity", "Problem with captcha init")
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

    private fun executeLoginAction() {
        lifecycleScope.launch {
            recaptchaClient
                .execute(RecaptchaAction.LOGIN)
                .onSuccess { token ->
                    Toast.makeText(applicationContext, "Captcha is correct", Toast.LENGTH_SHORT).show()
                }
                .onFailure { exception ->
                    Toast.makeText(applicationContext, "Captcha is incorrect", Toast.LENGTH_SHORT).show()
                }
        }
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