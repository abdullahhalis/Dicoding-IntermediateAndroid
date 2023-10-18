package com.dicoding.mystory.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.Repository
import com.dicoding.mystory.data.pref.UserModel
import com.dicoding.mystory.data.response.LoginResponse
import com.dicoding.mystory.utils.Event
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: Repository) : ViewModel() {
    val responseMessage : LiveData<Event<String>> = repository.responseMessage
    val isLoading : LiveData<Boolean> = repository.isLoading
    val loginResponse : LiveData<Event<LoginResponse>> = repository.loginResponse

    fun userLogin(email: String, password: String) {
        viewModelScope.launch {
            repository.userLogin(email, password)
        }
    }

    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }
}