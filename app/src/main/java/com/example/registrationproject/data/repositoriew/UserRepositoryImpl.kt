package com.example.registrationproject.data.repositoriew

import android.content.Context
import com.example.registrationproject.domain.model.User
import com.example.registrationproject.domain.repository.UserRepository

class UserRepositoryImpl(context: Context): UserRepository  {
    companion object {
        const val NAME_KEY = "user_name"
        const val SURNAME_KEY = "user_surname"
        const val DATE_KEY = "user_date"
        const val PASSWORD_KEY = "user_password"
        const val SHARED_PREFS_NAME = "shared preferences name"
    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveUserData(user: User) {
        sharedPreferences.edit()
            .putString(NAME_KEY, user.name)
            .putString(SURNAME_KEY, user.surname)
            .putString(DATE_KEY, user.date)
            .putString(PASSWORD_KEY, user.password)
            .apply()
    }

    override fun getUserData(): User {
        return User(
            sharedPreferences.getString(NAME_KEY, "not_found")!!,
            sharedPreferences.getString(SURNAME_KEY, "not_found")!!,
            sharedPreferences.getString(DATE_KEY, "not_found")!!,
            sharedPreferences.getString(PASSWORD_KEY, "not_found")!!
        )
    }

}