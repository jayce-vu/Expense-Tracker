package com.example.expenseTracker.data.services.postModel

import java.io.File
import java.time.LocalDateTime

data class PostExpense(
    val categoryId: String,
    val amount: Double,
    val description: String,
    val date: LocalDateTime,
    val invoices: List<File>
)

