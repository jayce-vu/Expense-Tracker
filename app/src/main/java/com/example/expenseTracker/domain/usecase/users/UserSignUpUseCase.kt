package com.example.expenseTracker.domain.usecase.users

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.responseModels.SignupResponseModel
import com.example.expenseTracker.domain.repositories.UserRepository

class UserSignUpUseCase(private val userRepository: UserRepository) {
    suspend fun execute(email: String, password: String, name: String): NetworkResult<SignupResponseModel>
        {
            userRepository.signup(email, password, name).let{ response ->
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