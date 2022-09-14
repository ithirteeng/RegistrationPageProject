package com.example.registrationproject.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationproject.R
import com.example.registrationproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra(RegistrationActivity.INTENT_KEY)
        greetingButtonTouchListener(name!!)
    }

    private fun greetingButtonTouchListener(message: String) {
        binding.greetingButton.setOnClickListener {
            val tempString = resources.getString(R.string.greeting_text)
            Toast.makeText(this, "$tempString, $message", Toast.LENGTH_SHORT).show()
        }
    }
}