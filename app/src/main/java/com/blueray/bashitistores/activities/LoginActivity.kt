package com.blueray.bashitistores.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.blueray.bashitistores.R
import com.blueray.bashitistores.databinding.ActivityLoginBinding
import com.blueray.bashitistores.helpers.HelpersUtils.SHARED_PREF
import com.blueray.bashitistores.helpers.ViewUtils.hide
import com.blueray.bashitistores.helpers.ViewUtils.show
import com.blueray.bashitistores.model.NetworkResults
import com.blueray.bashitistores.viewModels.AppViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.signupBtn.setOnClickListener {
            binding.pd.show()
            startActivity(Intent(this,SignUpActivity::class.java))
        }


        binding.loginBtn.setOnClickListener {

            viewModel.login(binding.emailET.text.toString(), binding.passwordET.text.toString(),"")


        }

getData()
    }


    private fun getData() {
        viewModel.getLoginLiveData().observe(this){

            when(it){
                is NetworkResults.Success ->{

                    binding.pd.hide()

                    if(it.data.status==200){

                        Toast.makeText(this,it.data.message.toString(),Toast.LENGTH_LONG).show()

                        startActivity(Intent(this,HomeActivity::class.java))
saveUserData(it.data.data?.uid.toString(), it.data.data?.token.toString())

                    } else if (it.data.status == 400){
                        Toast.makeText(this,it.data.message.toString(),Toast.LENGTH_LONG).show()

                    }
                }
                is NetworkResults.Error->{
                    binding.pd.hide()

                    Log.e("ayham", it.exception.toString())
                }
            }
        }
    }

    private fun saveUserData(user: String,token: String) {
        val sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("uid", user.toString())
            putString("token", token.toString())
        }.apply()
    }

}