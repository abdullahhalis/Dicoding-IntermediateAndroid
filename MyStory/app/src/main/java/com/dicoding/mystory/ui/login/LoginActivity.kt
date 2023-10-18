package com.dicoding.mystory.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dicoding.mystory.R
import com.dicoding.mystory.data.pref.UserModel
import com.dicoding.mystory.databinding.ActivityLoginBinding
import com.dicoding.mystory.ui.main.MainActivity
import com.dicoding.mystory.utils.ViewModelFactory
import com.dicoding.mystory.utils.showLoading
import com.dicoding.mystory.utils.showToast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels { ViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }
        val messageText = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }
        val emailText = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }
        val emailEdit = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }
        val passwordText = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }
        val passwordEdit = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }
        val loginButton = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }

        AnimatorSet().apply {
            playSequentially(title, messageText, emailText, emailEdit, passwordText, passwordEdit, loginButton)
            start()
        }
    }

    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener{
                when {
                    emailEditText.text?.length == 0 -> emailEditText.error = getString(R.string.field_required)
                    passwordEditText.text?.length == 0 -> passwordEditText.error = getString(R.string.field_required)
                    else ->{
                        val email = emailEditText.text.toString()
                        val password = passwordEditText.text.toString()
                        loginViewModel.isLoading.observe(this@LoginActivity) {
                            progressBar.showLoading(it)
                        }
                        loginViewModel.userLogin(email, password)
                        loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
                            if (!response.error){
                                val userModel = UserModel(
                                    response.loginResult.name,
                                    response.loginResult.token
                                )
                                loginViewModel.saveSession(userModel)
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                        }
                        loginViewModel.responseMessage.observe(this@LoginActivity) { message ->
                            message.getContentIfNotHandled()?.let {
                                showToast(this@LoginActivity, it)
                            }
                        }
                    }
                }
            }
        }
    }
}