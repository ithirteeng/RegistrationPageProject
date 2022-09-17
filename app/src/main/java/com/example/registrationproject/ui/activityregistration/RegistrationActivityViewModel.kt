package com.example.registrationproject.ui.activityregistration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.registrationproject.data.repositories.UserRepositoryImpl
import com.example.registrationproject.domain.model.User
import com.example.registrationproject.domain.usecase.DataValidatorUseCase
import com.example.registrationproject.domain.usecase.GetUserNameUseCase
import com.example.registrationproject.domain.usecase.SaveUserDataUseCase
import com.example.registrationproject.domain.validator.PasswordValidator
import com.example.registrationproject.domain.validator.Validator

class RegistrationActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepositoryImpl by lazy {
        UserRepositoryImpl(application.applicationContext)
    }

    private val saveUserDataUseCase by lazy {
        SaveUserDataUseCase(userRepositoryImpl)
    }

    private val getUserDataUseCase by lazy {
        GetUserNameUseCase(userRepositoryImpl)
    }

    private val dataValidatorUseCase by lazy {
        DataValidatorUseCase()
    }

    fun validateData(correctValidator: Validator, data: String): Int {
        return dataValidatorUseCase.execute(correctValidator, data)
    }

    fun validateData(
        passwordValidator: PasswordValidator,
        firstPassword: String,
        secondPassword: String
    ): Int {
        return dataValidatorUseCase.execute(passwordValidator, firstPassword, secondPassword)
    }

    fun getUserNameSurname(): String {
        return getUserDataUseCase.execute()
    }

    fun saveUserData(user: User) {
        saveUserDataUseCase.execute(user)
    }
}