package com.example.registrationproject.domain.validator

import com.example.registrationproject.domain.model.ErrorType

class PasswordValidator : Validator {
    override fun checkValidity(string: String): String {
        val upperCasePattern = Regex("([A-Z]|[А-Я])+")
        val lowerCasePattern = Regex("([a-z]|[а-я])+")
        val numberPattern = Regex("\\d+")
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR.toString()
        } else if (string.contains(upperCasePattern)
            && string.contains(lowerCasePattern)
            && string.contains(numberPattern)
        ) {
            ErrorType.OK
        } else {
            ErrorType.PASSWORD_ERROR.toString()
        }

    }

    fun checkEqualityPasswords(firstPassword: String, secondPassword: String): String {
        return if (firstPassword == secondPassword && firstPassword.isNotEmpty()) {
            ErrorType.OK
        } else {
            ErrorType.CONFIRMATION_ERROR.toString()
        }
    }

}