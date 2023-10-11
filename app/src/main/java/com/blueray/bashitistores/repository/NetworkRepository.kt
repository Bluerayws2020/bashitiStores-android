package com.blueray.bashitistores.repository

import android.util.Log
import com.blueray.bashitistores.api.ApiClient
import com.blueray.bashitistores.model.NetworkResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Part

object NetworkRepository {


    suspend fun loginUser(
        username:String,
        password:String,
        player_id:String

    ): NetworkResults<NetworkResults.LoginResponseModel> {
        return withContext(Dispatchers.IO){

            val lang="ar"
            val phoneType= "1"
            val phoneTypeRequestBody=phoneType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val langRequestBody=lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val usernameRequestBody=username.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val passwordRequestBody=password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val player_idBody=player_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())


            try {
                val result= ApiClient.retrofitService.login(
                    langRequestBody,
                    usernameRequestBody,
                    passwordRequestBody,
                    player_idBody
                )

                Log.d("UUUSADS",username+password)
                NetworkResults.Success(result)


            }catch (e:Exception){
                NetworkResults.Error(e)
            }
        }
    }
    suspend fun chekcToken(
        token:String,
        uid:String,


    ): NetworkResults<NetworkResults.Messg> {
        return withContext(Dispatchers.IO){

            val lang="ar"
            val phoneType= "1"
            val langRequestBody=lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val tokenBody=token.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val uidBody=uid.toRequestBody("multipart/form-data".toMediaTypeOrNull())


            try {
                val result= ApiClient.retrofitService.checkUser(
                    langRequestBody,
                    uidBody,tokenBody

                )
                NetworkResults.Success(result)

            }catch (e:Exception){
                NetworkResults.Error(e)
            }
        }
    }


    suspend fun createAccount(
        username:String,
        password:String,
        phone_number:String,
        mobile_type:String,
        player_id:String,
        birth_date:String,

        ): NetworkResults<NetworkResults.LoginResponseModel> {
        return withContext(Dispatchers.IO){

            val lang="ar"
            val phoneType= "1"
            val phoneTypeRequestBody=phoneType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val langRequestBody=lang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val usernameRequestBody=username.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val passwordRequestBody=password.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            val phone_numberBody=phone_number.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val mobile_typeBody=mobile_type.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val player_idBody=player_id.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val birth_dateBody=birth_date.toRequestBody("multipart/form-data".toMediaTypeOrNull())


            try {
                val result= ApiClient.retrofitService.createAccoutnt(
                    langRequestBody,
                    usernameRequestBody,
                    passwordRequestBody,
                    phone_numberBody,
                    mobile_typeBody,
                    player_idBody
                    ,birth_dateBody

                )
                NetworkResults.Success(result)

            }catch (e:Exception){
                NetworkResults.Error(e)
            }
        }
    }
}
