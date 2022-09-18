package com.example.registrationproject.ui.activityregistration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.registrationproject.data.repositories.UserRepositoryImpl
import com.example.registrationproject.domain.model.User
import com.example.registrationproject.domain.validator.PasswordValidator
import com.example.registrationproject.domain.validator.Validator
import com.example.registrationproject.ui.usecasefactory.UseCasesFactory

class RegistrationActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepositoryImpl by lazy {
        UserRepositoryImpl(application.applicationContext)
    }

    private val useCasesFactory by lazy {
        UseCasesFactory(userRepositoryImpl)
    }

    private val saveUserDataUseCase = useCasesFactory.createSaveUserDataUC()

    private val getUserNameUseCase = useCasesFactory.createGetUserNameUC()

    private val dataValidatorUseCase = useCasesFactory.createDataValidatorUC()

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
        return getUserNameUseCase.execute()
    }

    fun saveUserData(user: User) {
        saveUserDataUseCase.execute(user)
    }
}