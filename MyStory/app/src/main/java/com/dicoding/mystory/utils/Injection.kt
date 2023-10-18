package com.dicoding.mystory.utils

import android.content.Context
import com.dicoding.mystory.data.Repository
import com.dicoding.mystory.data.pref.UserPreferences
import com.dicoding.mystory.data.pref.dataStore
import com.dicoding.mystory.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository{
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        ApiConfig.token = runBlocking { userPreferences.getSession().first().token }.toString()
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService, userPreferences)
    }
}