package com.example.expenseTracker.data.services.responseModels

import com.example.expenseTracker.data.models.UserInfoModel

data class LoginResponseModel (val token: String, val user: UserInfoModel)