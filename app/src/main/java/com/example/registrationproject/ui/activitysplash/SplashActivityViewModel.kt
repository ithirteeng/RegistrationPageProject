package com.example.registrationproject.ui.activitysplash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.registrationproject.data.repositories.UserRepositoryImpl
import com.example.registrationproject.domain.model.User
import com.example.registrationproject.ui.usecasefactory.UseCasesFactory

class SplashActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepositoryImpl by lazy {
        UserRepositoryImpl(application.applicationContext)
    }

    private val useCasesFactory by lazy {
        UseCasesFactory(userRepositoryImpl)
    }

    private val getUserNameUseCase = useCasesFactory.createGetUserNameUC()

    fun getUserNameSurname(): String = getUserNameUseCase.execute()


    fun getUserData(): User = userRepositoryImpl.getUserData()

}