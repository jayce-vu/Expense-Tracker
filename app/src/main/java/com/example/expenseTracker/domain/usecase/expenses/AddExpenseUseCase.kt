package com.example.expenseTracker.domain.usecase.expenses

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.postModel.PostExpense
import com.example.expenseTracker.domain.repositories.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(private val expenseRepository: ExpenseRepository) {
    suspend fun excuse(postExpense: PostExpense):NetworkResult<Boolean>{
        return expenseRepository.createExpense(postExpense)
    }
}