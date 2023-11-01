package com.dicoding.mystory.utils

import android.content.Context
import com.dicoding.mystory.data.AuthRepository
import com.dicoding.mystory.data.StoryRepository
import com.dicoding.mystory.data.database.StoryDatabase
import com.dicoding.mystory.data.pref.UserPreferences
import com.dicoding.mystory.data.pref.dataStore
import com.dicoding.mystory.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository{
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        ApiConfig.token = runBlocking { userPreferences.getSession().first().token }.toString()
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService, userPreferences)
    }
    fun provideStoryRepository(context: Context): StoryRepository{
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        ApiConfig.token = runBlocking { userPreferences.getSession().first().token }.toString()
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getDatabase(context)
        return StoryRepository.getInstance(apiService, userPreferences, database)
    }
}