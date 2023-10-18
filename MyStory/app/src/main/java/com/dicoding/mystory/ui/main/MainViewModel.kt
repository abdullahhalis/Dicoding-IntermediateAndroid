package com.dicoding.mystory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.mystory.data.Repository
import com.dicoding.mystory.data.pref.UserModel
import com.dicoding.mystory.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    val listStory : LiveData<List<ListStoryItem>> = repository.listStory
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun getAllStories(){
        viewModelScope.launch {
            repository.getAllStories()
        }
    }
}