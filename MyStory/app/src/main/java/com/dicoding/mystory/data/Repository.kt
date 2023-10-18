package com.dicoding.mystory.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mystory.data.pref.UserModel
import com.dicoding.mystory.data.pref.UserPreferences
import com.dicoding.mystory.data.response.ListStoryItem
import com.dicoding.mystory.data.response.LoginResponse
import com.dicoding.mystory.data.response.ResponseMessage
import com.dicoding.mystory.data.response.Story
import com.dicoding.mystory.data.retrofit.ApiService
import com.dicoding.mystory.utils.Event
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class Repository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
){
    private val _responseMessage = MutableLiveData<Event<String>>()
    val responseMessage : LiveData<Event<String>> = _responseMessage

    private val _registerResponse = MutableLiveData<Event<ResponseMessage>>()
    val registerResponse : LiveData<Event<ResponseMessage>> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse : LiveData<LoginResponse> = _loginResponse

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory : LiveData<List<ListStoryItem>> = _listStory

    private val _detailStory = MutableLiveData<Story>()
    val detailStory: LiveData<Story> = _detailStory

    private val _uploadResponse = MutableLiveData<ResponseMessage>()
    val uploadResponse : LiveData<ResponseMessage> = _uploadResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    suspend fun registerUser(name: String, email: String, password: String) {
        _isLoading.value = true
        try {
            val response = apiService.register(name, email, password)
            _registerResponse.value = Event(response)
            _isLoading.value = false
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ResponseMessage::class.java)
            _registerResponse.value = Event(errorResponse)
            _isLoading.value = false
        }
    }

    suspend fun userLogin(email: String, password: String) {
        _isLoading.value = true
        try {
            val response = apiService.login(email, password)
            _loginResponse.value = response
            _responseMessage.value = Event(response.message)
            _isLoading.value = false
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ResponseMessage::class.java)
            _responseMessage.value = Event(errorResponse.message)
            _isLoading.value = false
        }
    }

    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    suspend fun getAllStories() {
        _isLoading.value = true
        try {
            val response = apiService.getStories()
            _listStory.value = response.listStory
            _isLoading.value = false
        } catch (e: HttpException){
            Log.e("list story", "onFailure: ${e.message}, token: ${userPreferences.getSession().first().token}")
            _isLoading.value = false
        }
    }

    suspend fun getDetailStory(id: String){
        _isLoading.value = true
        try {
            val response = apiService.getDetailStory(id).story
            _detailStory.value = response
            _isLoading.value = false
        }catch (e: HttpException) {
            Log.e("detail story", "onFailure: ${e.message}")
            _isLoading.value = false
        }
    }

    suspend fun uploadFile(description: RequestBody, file: MultipartBody.Part){
        _isLoading.value = true
        try {
            val successResponse = apiService.uploadImage(description, file)
            _uploadResponse.value = successResponse
            _responseMessage.value = Event(successResponse.message)
            _isLoading.value = false
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse =  Gson().fromJson(errorBody, ResponseMessage::class.java)
            _uploadResponse.value = errorResponse
            _responseMessage.value = Event(errorResponse.message)
            _isLoading.value = false
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ):Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(apiService, userPreferences)
            }.also { INSTANCE = it }
    }
}