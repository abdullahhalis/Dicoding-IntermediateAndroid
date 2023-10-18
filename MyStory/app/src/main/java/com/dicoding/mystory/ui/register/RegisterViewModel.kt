package com.dicoding.mystory.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.Repository
import com.dicoding.mystory.data.response.ResponseMessage
import com.dicoding.mystory.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    val registerResponse: LiveData<Event<ResponseMessage>> = repository.registerResponse
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun registerUser(name: String, email: String, password: String){
        viewModelScope.launch {
            repository.registerUser(name, email, password)
        }
    }
}