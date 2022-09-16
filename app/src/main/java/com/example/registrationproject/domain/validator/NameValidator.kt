package com.example.registrationproject.domain.validator

import com.example.registrationproject.domain.model.ErrorType

class NameValidator : Validator {
    override fun checkValidity(string: String): String {
        val pattern = Regex("[A-ZА-Я]?([a-zа-я])*\\s*")
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR.toString()
        } else if (string.matches(pattern)) {
            isFirstLetterUpperCase(string)
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