package com.dicoding.mystory.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mystory.data.AuthRepository
import com.dicoding.mystory.ui.login.LoginViewModel
import com.dicoding.mystory.ui.register.RegisterViewModel
import com.dicoding.mystory.ui.splash.SplashViewModel
import java.lang.IllegalArgumentException

class AuthViewModelFactory private constructor(
    private val authRepository: AuthRepository
)  : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(authRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: AuthViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): AuthViewModelFactory {
            if (INSTANCE == null) {
                synchronized(AuthViewModelFactory::class.java) {
                    INSTANCE = AuthViewModelFactory(Injection.provideAuthRepository(context))
                }
            }
            return INSTANCE as AuthViewModelFactory
        }
    }
}