package com.blueray.bashitistores.viewModels

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blueray.bashitistores.helpers.HelpersUtils
import com.blueray.bashitistores.helpers.HelpersUtils.SHARED_PREF
import com.blueray.bashitistores.helpers.HelpersUtils.getAndroidID
import com.blueray.bashitistores.model.NetworkResults
import com.blueray.bashitistores.repository.NetworkRepository
import kotlinx.coroutines.launch

class AppViewModel(application: Application):AndroidViewModel(application) {
    /** the launch of the coroutine is recommended to be in the viewModel Layer not in the UI Layer
    because of the life cycle constraints if launched in a UI component */

    private val deviceId = getAndroidID(application.applicationContext)
    private val repo = NetworkRepository
    private val language = "ar"


    private val sharedpreferences: SharedPreferences =
        application.getSharedPreferences(SHARED_PREF, AppCompatActivity.MODE_PRIVATE)
    private val loginLiveData= MutableLiveData<NetworkResults<NetworkResults.LoginResponseModel>>()
    private val createAccountLive= MutableLiveData<NetworkResults<NetworkResults.LoginResponseModel>>()

    private val chceckUserLive= MutableLiveData<NetworkResults<NetworkResults.Messg>>()

    fun login(email:String,password:String,playerId:String){
        viewModelScope.launch {
            loginLiveData.value=repo.loginUser(
                email,password,playerId
            )
        }
    }
    fun getLoginLiveData()=loginLiveData



    fun createAccount(email:String,password:String,phone:String,birthdate:String,player_id:String){
        viewModelScope.launch {
            createAccountLive.value=repo.createAccount(
                email,password,phone,"1",player_id,birthdate
            )
        }
    }
    fun getCreateAccount()=createAccountLive




    fun chcekUsre(token:String,uid:String){
        viewModelScope.launch {
            chceckUserLive.value=repo.chekcToken(
              token,uid
            )
        }
    }
    fun getCheckToken()=chceckUserLive
}