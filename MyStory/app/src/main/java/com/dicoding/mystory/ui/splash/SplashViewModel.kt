package com.dicoding.mystory.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.mystory.data.Repository
import com.dicoding.mystory.data.pref.UserModel

class SplashViewModel(private val repository: Repository): ViewModel() {
    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}