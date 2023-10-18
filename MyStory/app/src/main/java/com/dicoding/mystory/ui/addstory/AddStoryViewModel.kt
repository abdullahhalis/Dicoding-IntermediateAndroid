package com.dicoding.mystory.ui.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.Repository
import com.dicoding.mystory.data.response.ResponseMessage
import com.dicoding.mystory.utils.Event
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: Repository) : ViewModel() {
    val uploadResponse: LiveData<ResponseMessage> = repository.uploadResponse
    val responseMessage: LiveData<Event<String>> = repository.responseMessage
    val isLoading : LiveData<Boolean> = repository.isLoading

    fun uploadFile(description: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            repository.uploadFile(description, file)
        }
    }
}