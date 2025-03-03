package com.example.expenseTracker.data.repositories

import android.util.Log
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.models.UserInfoModel
import com.example.expenseTracker.data.services.UserService
import com.example.expenseTracker.data.services.postModel.PostLogin
import com.example.expenseTracker.data.services.postModel.PostSignup
import com.example.expenseTracker.data.services.responseModels.LoginResponseModel
import com.example.expenseTracker.data.services.responseModels.SignupResponseModel
import com.example.expenseTracker.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    private val _userInfo = MutableStateFlow<UserInfoModel?>(null)

    override suspend fun login(
        email: String,
        password: String
    ): NetworkResult<LoginResponseModel> {
        val response = userService.login(PostLogin(email, password))
        if (response.isSuccessful) {
            val result = response.body()
            result?.let { successData ->
                if(successData.isSuccess()){
                    Log.d("UserRepositoryImpl", "login: ${successData.data.user}")
                    _userInfo.update {
                        successData.data.user
                    }
                    return (NetworkResult.Success(response.body()?.data))
                } else {
                    return (NetworkResult.Error(successData.message))
                }
            }
        }
        return (NetworkResult.Error(response.errorBody().toString()))
    }

    override suspend fun signup(email: String, password: String, name: String): NetworkResult<SignupResponseModel> {
        val response = userService.signup(PostSignup(name, email, password))
        if (response.isSuccessful) {
            val result = response.body()
            result?.let {
                if(it.isSuccess()){
                    return (NetworkResult.Success(response.body()?.data))
                } else {
                    return (NetworkResult.Error(it.message))
                }
            }
        }
        return (NetworkResult.Error(response.errorBody().toString()))
    }

    override suspend fun logout() {
        // TODO("Not yet implemented")
    }

    override fun getUserInfo(): StateFlow<UserInfoModel?> {
        return _userInfo;
    }

    override suspend fun getToken(): String? {
        // TODO("Not yet implemented")
        return null
    }
}