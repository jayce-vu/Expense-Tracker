package com.example.expenseTracker.data.services.postModel

import java.io.File
import java.time.LocalDateTime

data class ExpenseUpdateRequest(
    val categoryId: String,
    val amount: Double,
    val date: LocalDateTime,
    val description: String,
    val invoices: List<File>
)
