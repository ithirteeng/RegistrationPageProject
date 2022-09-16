package com.example.registrationproject.domain.repository

import com.example.registrationproject.domain.model.User

interface UserRepository {
    fun saveUserData(user: User)

    fun getUserData(): User
}