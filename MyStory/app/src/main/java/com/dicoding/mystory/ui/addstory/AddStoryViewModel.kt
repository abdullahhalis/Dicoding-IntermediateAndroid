package com.dicoding.mystory.ui.addStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.StoryRepository
import com.dicoding.mystory.data.response.ResponseMessage
import com.dicoding.mystory.utils.Event
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    val uploadResponse: LiveData<ResponseMessage> = storyRepository.uploadResponse
    val responseMessage: LiveData<Event<String>> = storyRepository.responseMessage
    val isLoading : LiveData<Boolean> = storyRepository.isLoading

    fun uploadFile(description: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            storyRepository.uploadFile(description, file)
        }
    }
}