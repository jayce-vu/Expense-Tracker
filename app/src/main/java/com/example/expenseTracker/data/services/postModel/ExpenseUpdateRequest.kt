package com.example.expenseTracker.data.services.postModel

import java.io.File

data class ExpenseUpdateRequest(
    val categoryId: String,
    val amount: Double,
    val date: String,
    val description: String,
    val invoices: List<File>
)
