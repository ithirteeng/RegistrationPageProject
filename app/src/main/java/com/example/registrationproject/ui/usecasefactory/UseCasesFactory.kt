package com.example.registrationproject.ui.usecasefactory

import com.example.registrationproject.data.repositories.UserRepositoryImpl
import com.example.registrationproject.domain.usecase.DataValidatorUseCase
import com.example.registrationproject.domain.usecase.GetUserNameUseCase
import com.example.registrationproject.domain.usecase.SaveUserDataUseCase

class UseCasesFactory(private val userRepositoryImpl: UserRepositoryImpl) {

    fun createSaveUserDataUC() = SaveUserDataUseCase(userRepositoryImpl)

    fun createGetUserNameUC() = GetUserNameUseCase(userRepositoryImpl)

    fun createDataValidatorUC() = DataValidatorUseCase()

}