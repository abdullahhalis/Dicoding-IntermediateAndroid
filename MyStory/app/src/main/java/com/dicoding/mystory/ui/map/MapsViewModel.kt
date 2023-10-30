package com.dicoding.mystory.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.StoryRepository
import com.dicoding.mystory.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {
    val listStoryWithLocation: LiveData<List<ListStoryItem>> = storyRepository.listStoryWithLocation
    val isLoading: LiveData<Boolean> = storyRepository.isLoading

    fun getAllStoriesWithLocation() {
        viewModelScope.launch {
            storyRepository.getAllStoriesWithLocation()
        }
    }
}