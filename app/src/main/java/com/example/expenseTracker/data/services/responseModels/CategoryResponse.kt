package com.example.expenseTracker.data.services.responseModels

data class CategoryResponse(
    val id: String = "",
    val name: String = "",
    val type: String = "",
    val isSystem: Int = 0,
    val own: String = "",
){
    fun isSystemCategory(): Boolean {
        return isSystem == 1
    }
}
