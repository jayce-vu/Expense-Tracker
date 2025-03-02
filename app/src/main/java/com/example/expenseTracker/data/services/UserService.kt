package com.example.expenseTracker.data.services

import com.example.expenseTracker.data.models.BaseResponse
import com.example.expenseTracker.data.models.UserInfoModel
import com.example.expenseTracker.data.services.postModel.PostLogin
import com.example.expenseTracker.data.services.postModel.PostSignup
import com.example.expenseTracker.data.services.responseModels.LoginResponseModel
import com.example.expenseTracker.data.services.responseModels.SignupResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("profile")
    suspend fun profile(): Response<BaseResponse<UserInfoModel>>

    @POST("login")
    suspend fun login(
        @Body postLogin: PostLogin,
    ): Response<BaseResponse<LoginResponseModel>>

    @POST("signup")
    suspend fun signup(
        @Body postSignup: PostSignup,
    ): Response<BaseResponse<SignupResponseModel>>

    @POST("logout")
    suspend fun logout(): Response<BaseResponse<Any>>
}