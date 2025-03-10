package com.example.expenseTracker.data.services.postModel

data class CategoryRequest(
    val name: String,
    val type: String,
    val isSystem: Boolean = false
)
