package com.example.expenseTracker.data.models

data class BaseResponse<T>(
    val status: String,
    val message: String,
    val data: T,
    val error: String
){
    fun isSuccess(): Boolean {
        return status == "success"
    }
}