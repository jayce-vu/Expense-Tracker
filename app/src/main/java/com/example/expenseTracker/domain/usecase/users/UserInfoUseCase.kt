package com.example.expenseTracker.domain.usecase.users

import com.example.expenseTracker.data.models.UserInfoModel
import com.example.expenseTracker.domain.repositories.UserRepository
import kotlinx.coroutines.flow.StateFlow

class UserInfoUseCase(private val userRepository: UserRepository) {
    fun excuse():StateFlow<UserInfoModel?>{
        return userRepository.getUserInfo()
    }
}