package com.example.expenseTracker.domain.usecase.expenses

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.responseModels.ExpenseDetailResponse
import com.example.expenseTracker.domain.repositories.ExpenseRepository
import javax.inject.Inject

class AllExpensesUseCase @Inject constructor(private val expenseRepository: ExpenseRepository) {
    suspend fun excuse(): NetworkResult<List<ExpenseDetailResponse>> {
        return expenseRepository.getAllExpenses()
    }
}