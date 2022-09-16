package com.example.registrationproject.domain.validator

import com.example.registrationproject.domain.model.ErrorType

class SurnameValidator: Validator {
    override fun checkValidity(string: String): Int {
        val pattern = Regex("[A-ZА-Я]?([a-zа-я])*\\s*")
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR
        } else if (string.matches(pattern)) {
            isFirstLetterUpperCase(string)
        } else {
            ErrorType.SECOND_SURNAME_ERROR
        }
    }

    private fun isFirstLetterUpperCase(string: String): Int {
        return if (string[0].isUpperCase()) {
            ErrorType.OK
        } else {
            ErrorType.FIRST_SURNAME_ERROR
        }
    }
}