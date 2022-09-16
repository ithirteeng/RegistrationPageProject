package com.example.registrationproject.domain.validator

import com.example.registrationproject.domain.model.ErrorType

class NameValidator : Validator {
    override fun checkValidity(string: String): Int {
        val pattern = Regex("[A-ZА-Я]?([a-zа-я])*\\s*")
        val digitPattern = Regex("\\d+")
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR
        } else if (string.matches(pattern)) {
            isFirstLetterUpperCase(string)
        } else if (string.contains(digitPattern)) {
            ErrorType.SECOND_NAME_ERROR
        } else {
            ErrorType.THIRD_NAME_ERROR
        }
    }

    private fun isFirstLetterUpperCase(string: String): Int {
        return if (string[0].isUpperCase()) {
            ErrorType.OK
        } else {
            ErrorType.FIRST_NAME_ERROR
        }
    }
}