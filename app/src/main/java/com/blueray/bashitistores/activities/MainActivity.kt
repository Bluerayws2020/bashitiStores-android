package com.blueray.bashitistores.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.blueray.bashitistores.databinding.ActivityMainBinding
import com.blueray.bashitistores.helpers.ContextWrapper
import com.blueray.bashitistores.helpers.HelpersUtils
import com.blueray.bashitistores.helpers.HelpersUtils.getLang
import com.blueray.bashitistores.helpers.ViewUtils.hide
import com.blueray.bashitistores.model.NetworkResults
import com.blueray.bashitistores.viewModels.AppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getData()

//        // wait 3 sec and navigate to the next activity
        lifecycleScope.launch{
            delay(3000)
            viewModel.chcekUsre(HelpersUtils.getToken(this@MainActivity),HelpersUtils.getUID(this@MainActivity))

        }


    }

    private fun startNextActivity(){
        startActivity(Intent(this,SplashActivity::class.java))
        finish()
    }




    private fun getData() {
        viewModel.getCheckToken().observe(this){

            when(it){
                is NetworkResults.Success ->{

                    if(it.data.status==200){


                        startActivity(Intent(this,HomeActivity::class.java))


                    } else {
                        startActivity(Intent(this,LoginActivity::class.java))


                    }
                }
                is NetworkResults.Error->{

                    Log.e("ayham", it.exception.toString())
                }
            }
        }
    }


}