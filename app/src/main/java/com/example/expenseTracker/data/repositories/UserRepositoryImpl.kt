package com.example.expenseTracker.data.repositories

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.models.UserInfoModel
import com.example.expenseTracker.data.services.UserService
import com.example.expenseTracker.data.services.postModel.PostLogin
import com.example.expenseTracker.data.services.responseModels.LoginResponseModel
import com.example.expenseTracker.domain.repositories.UserRepository
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    private var userInfo: UserInfoModel? = null
    override suspend fun login(
        email: String,
        password: String
    ): NetworkResult<LoginResponseModel> {
        val response = userService.login(PostLogin(email, password))
        if (response.isSuccessful) {
            val result = response.body()
            result?.let {
                if(it.isSuccess()){
                    userInfo = it.data.user
                    return (NetworkResult.Success(response.body()?.data))
                } else {
                    return (NetworkResult.Error(it.message))
                }
            }
        }
        return (NetworkResult.Error(response.errorBody().toString()))
    }

    override suspend fun signup(email: String, password: String, name: String) {
        // TODO("Not yet implemented")
    }

    override suspend fun logout() {
        // TODO("Not yet implemented")
    }

    override suspend fun getUserInfo(): UserInfoModel? {
        return userInfo;
    }

    override suspend fun getToken(): String? {
        // TODO("Not yet implemented")
        return null
    }
}