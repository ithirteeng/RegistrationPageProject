package com.example.registrationproject.domain.model

import com.example.registrationproject.R

class ErrorType {
    companion object {
        const val OK = R.string.ok_error_result
        const val FIRST_NAME_ERROR = R.string.first_name_error
        const val SECOND_NAME_ERROR = R.string.second_name_error
        const val THIRD_NAME_ERROR = R.string.third_name_error
        const val FIRST_SURNAME_ERROR = R.string.first_surname_error
        const val SECOND_SURNAME_ERROR = R.string.second_surname_error
        const val THIRD_SURNAME_ERROR = R.string.third_surname_error
        const val DATE_ERROR = R.string.date_error
        const val PASSWORD_ERROR = R.string.password_error
        const val CONFIRMATION_ERROR = R.string.confirmation_error
        const val EMPTINESS_ERROR = R.string.empty_error
        const val LENGTH_ERROR = R.string.length_error
    }

}