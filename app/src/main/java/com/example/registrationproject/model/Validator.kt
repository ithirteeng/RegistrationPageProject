package com.example.registrationproject.model

interface Validator {
    fun checkValidity(string: String): String
}