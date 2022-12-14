package com.example.registrationproject.domain.usecase

import com.example.registrationproject.domain.repository.UserRepository

class GetUserNameUseCase(private val userRepository: UserRepository ) {
    fun execute(): String {
        val user = userRepository.getUserData()
        return "${user.surname} ${user.name}"
    }
}