package me.podlesnykh.graduationproject.data.network.models

import com.google.gson.annotations.SerializedName

/**
 * Модель ответа с сообщением об ошибке
 */
data class ErrorResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
