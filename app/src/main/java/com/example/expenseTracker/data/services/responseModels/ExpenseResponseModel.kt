package com.example.expenseTracker.data.services.responseModels

data class ExpenseResponseModel(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val amount: Double,
    val date: String
)
