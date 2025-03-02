package com.example.expenseTracker.domain.repositories

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.models.UserInfoModel
import com.example.expenseTracker.data.services.responseModels.LoginResponseModel

interface UserRepository {
    suspend fun login(email: String, password: String): NetworkResult<LoginResponseModel>
    suspend fun signup(email: String, password: String, name: String)
    suspend fun logout()
    suspend fun getUserInfo(): UserInfoModel?
    suspend fun getToken(): String?
}