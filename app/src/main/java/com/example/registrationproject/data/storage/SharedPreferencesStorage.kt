package com.example.registrationproject.data.storage

import android.content.Context
import com.example.registrationproject.domain.model.User

class SharedPreferencesStorage(context: Context) : UserDataStorage {

    companion object {
        const val SHARED_PREFS_NAME = "shared preferences name"
    }

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveData(user: User) {
        sharedPreferences.edit()
            .putString(UserDataStorage.NAME_KEY, user.name)
            .putString(UserDataStorage.SURNAME_KEY, user.surname)
            .putString(UserDataStorage.DATE_KEY, user.date)
            .putString(UserDataStorage.PASSWORD_KEY, user.password)
            .apply()
    }

    override fun getData(): User {
        return User(
            sharedPreferences.getString(UserDataStorage.NAME_KEY, UserDataStorage.NOTHING)!!,
            sharedPreferences.getString(UserDataStorage.SURNAME_KEY, UserDataStorage.NOTHING)!!,
            sharedPreferences.getString(UserDataStorage.DATE_KEY, UserDataStorage.NOTHING)!!,
            sharedPreferences.getString(UserDataStorage.PASSWORD_KEY, UserDataStorage.NOTHING)!!
        )
    }

}