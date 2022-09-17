package com.example.registrationproject.ui.activitysplash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.registrationproject.data.repositories.UserRepositoryImpl
import com.example.registrationproject.domain.model.User
import com.example.registrationproject.domain.usecase.GetUserNameUseCase

class SplashActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepositoryImpl by lazy {
        UserRepositoryImpl(application.applicationContext)
    }

    private val getUserDataUseCase by lazy {
        GetUserNameUseCase(userRepositoryImpl)
    }

    fun getUserNameSurname(): String = getUserDataUseCase.execute()


    fun getUserData(): User = userRepositoryImpl.getUserData()

}