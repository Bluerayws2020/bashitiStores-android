package com.blueray.bashitistores.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.blueray.bashitistores.databinding.ActivitySignUpBinding
import com.blueray.bashitistores.helpers.HelpersUtils
import com.blueray.bashitistores.helpers.ViewUtils.hide
import com.blueray.bashitistores.helpers.ViewUtils.show
import com.blueray.bashitistores.model.NetworkResults
import com.blueray.bashitistores.viewModels.AppViewModel
import java.util.Calendar


class SignUpActivity : AppCompatActivity() {
    private val viewModel by viewModels<AppViewModel>()

    private lateinit var binding : ActivitySignUpBinding
var selectBirthdayBtn = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.barthDay.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, dayOfMonth ->
                val formattedYear = selectedYear.toString().takeLast(2)
                val formattedMonth = (selectedMonth + 1).toString().padStart(2, '0')
                val formattedDay = dayOfMonth.toString().padStart(2, '0')

                selectBirthdayBtn = "$formattedDay-$formattedMonth-$formattedYear"
            }, year, month, day).show()
        }


        binding.signupBtn.setOnClickListener {
binding.pd.show()
            viewModel.createAccount(binding.emailET.text.toString(), binding.PasswordET.text.toString(),binding.phoneNumberET.text.toString(),selectBirthdayBtn.toString(),"")


        }
getData()
binding.barthDay.setOnClickListener {
    showDateDialog { date ->
        binding.barthDay.text = date
        selectBirthdayBtn = date
    }
}


    }

    private fun showDateDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, { _, year, month, dayOfMonth ->
                onDateSelected("$year-${month + 1}-$dayOfMonth")
            }, mYear, mMonth, mDay
        )

        datePickerDialog.show()
    }
    private fun getData() {
        viewModel.getCreateAccount().observe(this){

            when(it){
                is NetworkResults.Success ->{
                    binding.pd.hide()
                    Toast.makeText(this,it.data.message, Toast.LENGTH_LONG).show()

                    if(it.data.status==200){

                        Toast.makeText(this,it.data.message.toString(), Toast.LENGTH_LONG).show()

                        startActivity(Intent(this,HomeActivity::class.java))

                        saveUserData(it.data.data?.uid.toString(), it.data.data?.token.toString())
                    }
                }
                is NetworkResults.Error->{
                    binding.pd.hide()

                }
            }
        }
    }

    private fun saveUserData(user: String,token: String) {
        val sharedPreferences = getSharedPreferences(HelpersUtils.SHARED_PREF, MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("uid", user.toString())
            putString("token", token.toString())
        }.apply()
    }

}