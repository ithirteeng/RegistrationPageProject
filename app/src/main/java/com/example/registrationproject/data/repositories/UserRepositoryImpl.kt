package com.example.registrationproject.data.repositories

import android.content.Context
import com.example.registrationproject.data.storage.SharedPreferencesStorage
import com.example.registrationproject.domain.model.User
import com.example.registrationproject.domain.repository.UserRepository

class UserRepositoryImpl(context: Context): UserRepository  {

    private val storage = SharedPreferencesStorage(context)

    override fun saveUserData(user: User) {
        storage.saveData(user)
    }

    override fun getUserData(): User {
        return storage.getData()
    }

}