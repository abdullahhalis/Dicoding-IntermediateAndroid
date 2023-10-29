package com.dicoding.mystory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.StoryRepository
import com.dicoding.mystory.data.pref.UserModel
import com.dicoding.mystory.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    val listStory : LiveData<List<ListStoryItem>> = storyRepository.listStory
    val isLoading: LiveData<Boolean> = storyRepository.isLoading

    fun getSession() : LiveData<UserModel> {
        return storyRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
        }
    }
    fun getAllStories(){
        viewModelScope.launch {
            storyRepository.getAllStories()
        }
    }
}