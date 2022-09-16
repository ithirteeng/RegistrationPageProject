package com.example.registrationproject.domain.validator

import com.example.registrationproject.domain.model.ErrorType

class PasswordValidator : Validator {
    override fun checkValidity(string: String): String {
        val pattern = Regex("(([a-z]|[а-я]|[A-Z]|[А-Я])+|[0-9]+)+")
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR.toString()
        } else if (string.matches(pattern)) {
            ErrorType.OK
        } else {
            ErrorType.PASSWORD_ERROR.toString()
        }

    }

    fun checkEqualityPasswords(firstPassword: String, secondPassword: String): String {
        return if (secondPassword.isEmpty() && firstPassword.isEmpty()) {
            ErrorType.EMPTINESS_ERROR.toString()
        } else {
            if (firstPassword == secondPassword && firstPassword.isNotEmpty()) {
                ErrorType.OK
            } else {
                ErrorType.CONFIRMATION_ERROR.toString()
            }
        }
    }

}