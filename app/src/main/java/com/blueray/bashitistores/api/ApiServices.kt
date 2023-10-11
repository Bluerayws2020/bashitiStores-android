package com.blueray.bashitistores.api

import com.blueray.bashitistores.model.NetworkResults
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {
    @Multipart
    @POST("app/checkToken")
    suspend fun checkUser(
        @Part("lang") lang:RequestBody,
        @Part("uid") username:RequestBody,
        @Part("token") password:RequestBody,
    ) : NetworkResults.Messg


    @Multipart
    @POST("app/login")
    suspend fun login(
        @Part("lang") lang:RequestBody,
        @Part("mail") username:RequestBody,
        @Part("password") password:RequestBody,
        @Part("player_id") mobile_type:RequestBody
    ) : NetworkResults.LoginResponseModel



    @Multipart
    @POST("app/SignUp")
    suspend fun createAccoutnt(
        @Part("lang") lang:RequestBody,
        @Part("mail") username:RequestBody,
        @Part("password") password:RequestBody,
        @Part("phone_number") phone_number:RequestBody,

        @Part("mobile_type") mobile_type:RequestBody,



        @Part("player_id") player_id:RequestBody,
        @Part("birth_date") birth_date:RequestBody




    ) : NetworkResults.LoginResponseModel

}