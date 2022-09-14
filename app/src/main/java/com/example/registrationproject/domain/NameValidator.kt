package com.example.registrationproject.domain

import com.example.registrationproject.model.ErrorType
import com.example.registrationproject.model.Validator

class NameValidator : Validator {
    override fun checkValidity(string: String): String {
        val pattern = Regex("[A-ZА-Я][a-zа-я]*\\s*")
        return if (string.matches(pattern)) {
            return isFirstLetterUpperCase(string)
        } else {
            ErrorType.SECOND_NAME_ERROR.toString()
        }
    }

    private fun isFirstLetterUpperCase(string: String): String {
        return if (string[0].isUpperCase()) {
            ErrorType.OK
        } else {
            ErrorType.FIRST_NAME_ERROR.toString()
        }
    }
}