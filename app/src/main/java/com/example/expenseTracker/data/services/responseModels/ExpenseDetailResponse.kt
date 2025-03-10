package com.example.expenseTracker.data.services.responseModels

data class ExpenseDetailResponse(
    val id: String = "",
    val categoryId: String = "",
    val categoryName: String = "",
    val isIncome: Boolean = false,
    val amount: Double = 0.0,
    val date: String = "",
    val description: String = "",
    val receiptUrl: String = "",
)
