package com.example.registrationproject.data.storage

import com.example.registrationproject.domain.model.User

interface UserDataStorage {

    companion object {
        const val NAME_KEY = "user_name"
        const val SURNAME_KEY = "user_surname"
        const val DATE_KEY = "user_date"
        const val PASSWORD_KEY = "user_password"
        const val NOTHING = "nothing"
    }

    fun saveData(user: User)

    fun getData(): User
}