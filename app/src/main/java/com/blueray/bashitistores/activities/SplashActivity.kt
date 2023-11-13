package com.blueray.bashitistores.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blueray.bashitistores.R
import com.blueray.bashitistores.databinding.ActivityMainBinding
import com.blueray.bashitistores.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var selectedLang = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // default is english
        binding.enCheckBox.isChecked = true

        // arabic lang choose
        binding.arLangBtn.setOnClickListener {
            binding.arCheckbox.isChecked = true
            selectedLang = "ar"
            binding.enCheckBox.isChecked = false
        }
        // english lang choose
        binding.enLangBtn.setOnClickListener {
            binding.enCheckBox.isChecked = true
            selectedLang = "en"
            binding.arCheckbox.isChecked = false
        }



        binding.getStartButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java).apply {
                putExtra("lang",selectedLang)
            })
        }
    }

}