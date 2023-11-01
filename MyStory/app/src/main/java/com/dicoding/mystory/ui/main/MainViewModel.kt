package com.dicoding.mystory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.mystory.data.StoryRepository
import com.dicoding.mystory.data.database.Story
import com.dicoding.mystory.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    val listStory : LiveData<PagingData<Story>> = storyRepository.getAllStories().cachedIn(viewModelScope)

    fun getSession() : LiveData<UserModel> {
        return storyRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
        }
    }
}