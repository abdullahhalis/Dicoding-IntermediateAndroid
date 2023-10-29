package com.dicoding.mystory.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dicoding.mystory.R
import com.dicoding.mystory.databinding.ActivityRegisterBinding
import com.dicoding.mystory.utils.AuthViewModelFactory
import com.dicoding.mystory.utils.showLoading
import com.dicoding.mystory.utils.showToast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels { AuthViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -80f, 80f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }
        val nameText = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }
        val nameEdit = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).apply {
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
        val signupButton = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).apply {
            duration = 100
            startDelay = 100
        }

        AnimatorSet().apply {
            playSequentially(title, nameText, nameEdit, emailText, emailEdit, passwordText, passwordEdit, signupButton )
            start()
        }
    }

    private fun postRegister() {
        binding.apply {
            registerViewModel.registerUser(
                nameEditText.text.toString(),
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
            )
        }
    }

    private fun setupAction() {
        binding.apply {
            registerButton.setOnClickListener{
                when {
                    nameEditText.text?.length == 0 -> nameEditText.error = getString(R.string.field_required)
                    emailEditText.text?.length == 0 -> emailEditText.error = getString(R.string.field_required)
                    passwordEditText.text?.length == 0 -> passwordEditText.error = getString(R.string.field_required)
                    else -> {
                        registerViewModel.isLoading.observe(this@RegisterActivity) {
                            progressBar.showLoading(it)
                        }
                        postRegister()
                        registerViewModel.registerResponse.observe(this@RegisterActivity) {
                            it.getContentIfNotHandled()?.let { response ->
                                showToast(this@RegisterActivity, response.message)
                                if(!response.error){
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}