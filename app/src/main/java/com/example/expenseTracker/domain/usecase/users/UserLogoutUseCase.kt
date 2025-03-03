package com.example.expenseTracker.domain.usecase.users

import com.example.expenseTracker.domain.repositories.UserRepository
import javax.inject.Inject

class UserLogoutUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun excuse(): Boolean{
        return userRepository.logout().data ?: false
    }
}