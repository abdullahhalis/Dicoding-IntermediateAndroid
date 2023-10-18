package com.dicoding.mystory.data.retrofit

import com.dicoding.mystory.data.response.DetailResponse
import com.dicoding.mystory.data.response.ListStoryResponse
import com.dicoding.mystory.data.response.LoginResponse
import com.dicoding.mystory.data.response.ResponseMessage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseMessage

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(): ListStoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id : String
    ): DetailResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
    ): ResponseMessage
}