package com.blueray.bashitistores.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.blueray.bashitistores.databinding.ActivityMainBinding
import com.blueray.bashitistores.helpers.ContextWrapper
import com.blueray.bashitistores.helpers.HelpersUtils.getLang
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // wait 3 sec and navigate to the next activity
        lifecycleScope.launch{
            delay(3000)
            startNextActivity()
        }

    }

    private fun startNextActivity(){
        startActivity(Intent(this,SplashActivity::class.java))
        finish()
    }


    // change the context
    override fun attachBaseContext(newBase: Context?) {
        val lang = getLang(newBase!!)
        val local = Locale(lang)
        val newContext = ContextWrapper.wrap(newBase, local)
        super.attachBaseContext(newContext)
    }

}