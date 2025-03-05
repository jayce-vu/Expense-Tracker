package com.example.expenseTracker.data.services.postModel

import java.io.File

data class PostExpense(
    val categoryId: String,
    val amount: Double,
    val description: String,
    val date: String,
    val invoices: List<File>
)

