package com.dicoding.mystory.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.Repository
import com.dicoding.mystory.data.response.Story
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository): ViewModel() {
    val isLoading : LiveData<Boolean> = repository.isLoading
    val detailStory: LiveData<Story> = repository.detailStory

    fun getDetailStory(id: String) {
        viewModelScope.launch {
            repository.getDetailStory(id)
        }
    }
}