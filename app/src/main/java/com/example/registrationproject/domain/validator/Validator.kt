package com.example.registrationproject.domain.validator

interface Validator {
    fun checkValidity(string: String): String
}