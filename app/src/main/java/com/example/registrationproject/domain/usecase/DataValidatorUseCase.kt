package com.example.registrationproject.domain.usecase

import com.example.registrationproject.domain.validator.PasswordValidator
import com.example.registrationproject.domain.validator.Validator

class DataValidatorUseCase {
    fun execute(correctValidator: Validator, data: String): Int {
        return correctValidator.checkValidity(data)
    }
    fun execute(passwordValidator: PasswordValidator, firstPassword: String, secondPassword: String): Int {
        return passwordValidator.checkEqualityPasswords(firstPassword, secondPassword)
    }
}