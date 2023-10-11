package com.blueray.bashitistores.model

sealed class NetworkResults<out R> {
    data class Success<out T>(val data: T) : NetworkResults<T>()
    data class Error(val exception: Exception) : NetworkResults<Nothing>()

    data class LoginResponseModel(
        val `data`: Data? = null,
        val message: String? = null,
        val status: Int
    )


    data class Data(
        val email: String,
        val phone: String,
        val role: String,
        val token: String,
        val uid: String,
        val user_name: String
    )

    data class Messg(

        val status: Int
    )
}