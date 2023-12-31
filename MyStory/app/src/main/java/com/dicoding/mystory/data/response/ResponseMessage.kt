package com.dicoding.mystory.data.response

import com.google.gson.annotations.SerializedName

data class ResponseMessage(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
