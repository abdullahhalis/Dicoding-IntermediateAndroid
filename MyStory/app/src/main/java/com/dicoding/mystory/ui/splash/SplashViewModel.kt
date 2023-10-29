package com.dicoding.mystory.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.mystory.data.AuthRepository
import com.dicoding.mystory.data.pref.UserModel

class SplashViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun getSession() : LiveData<UserModel> {
        return authRepository.getSession().asLiveData()
    }
}