package com.example.registrationproject.domain

import com.example.registrationproject.model.Validator

class NameValidator : Validator {
    override fun checkValidity(string: String): Boolean {
        val pattern = Regex("[А-Я]([а-я])*\\s*")
        return (pattern.matches(string))
    }
}