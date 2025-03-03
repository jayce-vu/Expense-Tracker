package com.example.expenseTracker.domain.usecase.users

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.responseModels.LoginResponseModel
import com.example.expenseTracker.domain.repositories.UserRepository

class UserLoginUseCase(private val userRepository: UserRepository) {
    suspend fun execute(email: String, password: String): NetworkResult<LoginResponseModel>
        {
            userRepository.login(email, password).let{ response ->
                when (response) {
                    is NetworkResult.Success -> {
                        return (NetworkResult.Success(response.data))
                    }

                    is NetworkResult.Error -> {
                        return (NetworkResult.Error(response.message))
                    }

                    is NetworkResult.Loading -> {
                        return (NetworkResult.Loading())
                    }
                }
            }
        }
}