package com.dicoding.mystory.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.AuthRepository
import com.dicoding.mystory.data.pref.UserModel
import com.dicoding.mystory.data.response.LoginResponse
import com.dicoding.mystory.utils.Event
import kotlinx.coroutines.launch

class LoginViewModel (private val authRepository: AuthRepository) : ViewModel() {
    val responseMessage : LiveData<Event<String>> = authRepository.responseMessage
    val isLoading : LiveData<Boolean> = authRepository.isLoading
    val loginResponse : LiveData<Event<LoginResponse>> = authRepository.loginResponse

    fun userLogin(email: String, password: String) {
        viewModelScope.launch {
            authRepository.userLogin(email, password)
        }
    }

    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            authRepository.saveSession(userModel)
        }
    }
}