package com.example.registrationproject.domain.uc

import com.example.registrationproject.domain.validator.PasswordValidator
import com.example.registrationproject.domain.validator.Validator

class DataValidatorUseCase {
    fun execute(correctValidator: Validator, data: String): String {
        return correctValidator.checkValidity(data)
    }
    fun execute(passwordValidator: PasswordValidator, firstPassword: String, secondPassword: String): String {
        return passwordValidator.checkEqualityPasswords(firstPassword, secondPassword)
    }
}