package com.blueray.bashitistores.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blueray.bashitistores.R
import com.blueray.bashitistores.databinding.ActivityMainBinding
import com.blueray.bashitistores.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.getStartButton.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

}