package com.example.registrationproject.domain.usecase

import com.example.registrationproject.domain.model.User
import com.example.registrationproject.domain.repository.UserRepository

class SaveUserDataUseCase(private val userRepository: UserRepository) {
    fun execute(user: User) {
        userRepository.saveUserData(user)
    }
}