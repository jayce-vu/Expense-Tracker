package com.example.expenseTracker.data.services.responseModels

data class ExpenseDetailResponse(
    val id: Int,
    val categoryId: Int,
    val categoryName: String,
    val amount: Double,
    val date: String,
    val description: String,
    val receiptUrl: String,
)
