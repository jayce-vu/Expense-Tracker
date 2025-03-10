package com.example.expenseTracker.domain.repositories

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.postModel.ExpenseUpdateRequest
import com.example.expenseTracker.data.services.postModel.PostExpense
import com.example.expenseTracker.data.services.responseModels.ExpenseDetailResponse
import com.example.expenseTracker.data.services.responseModels.ExpenseResponseModel

interface ExpenseRepository {

    suspend fun getAllExpenses(): NetworkResult<List<ExpenseResponseModel>>

    suspend fun createExpense(postExpense: PostExpense): NetworkResult<Boolean>

    suspend fun getExpenseById(id: String): NetworkResult<ExpenseDetailResponse>

    suspend fun updateExpense(
        id: String,
        expenseUpdateRequest: ExpenseUpdateRequest
    ): NetworkResult<Boolean>

    suspend fun deleteExpense(id: String): NetworkResult<Boolean>

    fun getAllCachedExpenses(): List<ExpenseResponseModel>
}