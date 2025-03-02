package com.example.expenseTracker.data.models

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
)
