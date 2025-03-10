package com.example.expenseTracker.presentation.features.transactionHistory.viewModel

import androidx.lifecycle.ViewModel
import com.example.expenseTracker.data.services.responseModels.ExpenseResponseModel
import com.example.expenseTracker.domain.repositories.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val expenseRepository: ExpenseRepository) : ViewModel() {
    private val _expensesState = MutableStateFlow<List<ExpenseResponseModel>>(emptyList())
    val expenses: StateFlow<List<ExpenseResponseModel>> = _expensesState.asStateFlow()
    init {
        _expensesState.update {
            expenseRepository.getAllCachedExpenses()
        }
    }
}