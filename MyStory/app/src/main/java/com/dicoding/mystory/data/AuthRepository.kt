package com.dicoding.mystory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mystory.data.pref.UserModel
import com.dicoding.mystory.data.pref.UserPreferences
import com.dicoding.mystory.data.response.LoginResponse
import com.dicoding.mystory.data.response.ResponseMessage
import com.dicoding.mystory.data.retrofit.ApiService
import com.dicoding.mystory.utils.Event
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class AuthRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {
    private val _responseMessage = MutableLiveData<Event<String>>()
    val responseMessage : LiveData<Event<String>> = _responseMessage

    private val _registerResponse = MutableLiveData<Event<ResponseMessage>>()
    val registerResponse : LiveData<Event<ResponseMessage>> = _registerResponse

    private val _loginResponse = MutableLiveData<Event<LoginResponse>>()
    val loginResponse : LiveData<Event<LoginResponse>> = _loginResponse

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
            _loginResponse.value = Event(response)
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

    companion object{
        @Volatile
        private var INSTANCE: AuthRepository? = null

        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ):AuthRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthRepository(apiService, userPreferences)
            }.also { INSTANCE = it }
    }
}