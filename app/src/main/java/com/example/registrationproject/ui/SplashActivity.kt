package com.example.registrationproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationproject.data.repositoriew.UserRepositoryImpl
import com.example.registrationproject.databinding.ActivitySplashBinding
import com.example.registrationproject.domain.uc.GetUserNameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val userRepositoryImpl by lazy {
        UserRepositoryImpl(applicationContext)
    }

    private val getUserDataUseCase by lazy {
        GetUserNameUseCase(userRepositoryImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            makeIntent()
            finish()
        }
    }

    private fun checkRegistrationCondition(): Boolean {
        return userRepositoryImpl.getUserData().name != UserRepositoryImpl.NOTHING
    }

    private fun makeIntent() {
        val intent = if (checkRegistrationCondition()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, RegistrationActivity::class.java)
        }
        intent.putExtra(MainActivity.INTENT_KEY, getUserDataUseCase.execute())
        startActivity(intent)
    }
}