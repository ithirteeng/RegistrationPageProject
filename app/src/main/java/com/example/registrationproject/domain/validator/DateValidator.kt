package com.example.registrationproject.domain.validator

import com.example.registrationproject.domain.model.ErrorType

class DateValidator : Validator {
    override fun checkValidity(string: String): String {
        val firstPattern = Regex("(0[1-9]|[1-2]\\d|3[0-1])\\.(01|03|05|07|08|10|12)\\.((20([01]\\d|2[0-2]))|(19\\d\\d))\\s*")
        val secondPattern = Regex("(0[1-9]|[12]\\d|30)\\.(04|06|09|11)\\.((20([01]\\d|2[0-2]))|(19\\d\\d))\\s*")
        val thirdPattern = Regex("(0[1-9]|[12]\\d)\\.(02)\\.((20([01]\\d|2[0-2]))|(19\\d\\d))\\s*")
        return if (string.isEmpty()) {
            ErrorType.EMPTINESS_ERROR.toString()
        } else if (string.matches(firstPattern)
            || string.matches(secondPattern)
            || string.matches(thirdPattern)) {
            ErrorType.OK
        } else {
            ErrorType.DATE_ERROR.toString()
        }
    }
}