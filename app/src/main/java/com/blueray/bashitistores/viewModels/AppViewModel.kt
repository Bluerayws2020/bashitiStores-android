package com.blueray.bashitistores.viewModels

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import com.blueray.bashitistores.helpers.HelpersUtils.SHARED_PREF
import com.blueray.bashitistores.helpers.HelpersUtils.getAndroidID
import com.blueray.bashitistores.repository.NetworkRepository

class AppViewModel(application: Application):AndroidViewModel(application) {
    /** the launch of the coroutine is recommended to be in the viewModel Layer not in the UI Layer
    because of the life cycle constraints if launched in a UI component */

    private val deviceId = getAndroidID(application.applicationContext)
    private val repo = NetworkRepository
    private val language = "ar"


    private val sharedpreferences: SharedPreferences =
        application.getSharedPreferences(SHARED_PREF, AppCompatActivity.MODE_PRIVATE)
}