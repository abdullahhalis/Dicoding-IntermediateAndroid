package com.dicoding.mystory.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.StoryRepository
import com.dicoding.mystory.data.response.Story
import kotlinx.coroutines.launch

class DetailViewModel(private val storyRepository: StoryRepository): ViewModel() {
    val isLoading : LiveData<Boolean> = storyRepository.isLoading
    val detailStory: LiveData<Story> = storyRepository.detailStory

    fun getDetailStory(id: String) {
        viewModelScope.launch {
            storyRepository.getDetailStory(id)
        }
    }
}