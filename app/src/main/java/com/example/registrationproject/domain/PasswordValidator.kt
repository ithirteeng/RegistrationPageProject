package com.example.registrationproject.domain

import com.example.registrationproject.model.Validator

class PasswordValidator : Validator {
    override fun checkValidity(string: String): Boolean {
        val upperCasePattern = Regex("([A-Z]|[А-Я])+")
        val lowerCasePattern = Regex("([a-z]|[а-я])+")
        val numberPattern = Regex("\\d+")
        return string.contains(upperCasePattern)
                && string.contains(lowerCasePattern)
                && string.contains(numberPattern)
    }

    fun checkEqualityPasswords(firstPassword: String, secondPassword: String): Boolean {
        return firstPassword == secondPassword && firstPassword.isNotEmpty()
    }

}