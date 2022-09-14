package com.example.registrationproject.domain

import com.example.registrationproject.model.Validator

class PasswordValidator : Validator {
    override fun checkValidity(string: String): Boolean {
        return true
    }

    fun checkEqualityPasswords(firstPassword: String, secondPassword: String): Boolean {
        return firstPassword == secondPassword
    }

}