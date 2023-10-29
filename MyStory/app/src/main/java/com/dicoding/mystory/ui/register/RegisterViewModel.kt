package com.dicoding.mystory.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.AuthRepository
import com.dicoding.mystory.data.response.ResponseMessage
import com.dicoding.mystory.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val registerResponse: LiveData<Event<ResponseMessage>> = authRepository.registerResponse
    val isLoading: LiveData<Boolean> = authRepository.isLoading

    fun registerUser(name: String, email: String, password: String){
        viewModelScope.launch {
            authRepository.registerUser(name, email, password)
        }
    }
}