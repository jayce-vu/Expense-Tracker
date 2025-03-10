package com.example.expenseTracker.presentation.features.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.responseModels.ExpenseDetailResponse
import com.example.expenseTracker.domain.repositories.ExpenseRepository
import com.example.expenseTracker.domain.usecase.expenses.AllExpensesUseCase
import com.example.expenseTracker.utils.LocaleHelper
import com.example.expenseTracker.utils.extensions.toCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val localeHelper: LocaleHelper,
    private val allExpensesUseCase: AllExpensesUseCase,
    private val expenseRepository: ExpenseRepository
) :
    ViewModel() {
    private val _state =
        MutableStateFlow(
            HomeViewState(
                income = 0.0,
                expense = 0.0,
            ),
        )
    val state: StateFlow<HomeViewState> = _state
    val expenseList: StateFlow<List<ExpenseDetailResponse>> =
        expenseRepository.getAllCachedExpenses()

    init {
        observe()
        fetchExpenses()
    }

    private fun observe() {
        viewModelScope.launch {
            expenseList.collect {
                val inComes = it.filter { expenseDetailResponse -> expenseDetailResponse.isIncome }
                    .map { item -> item.amount }.fold(0.0) { total, item -> total + item }
                val expenses =
                    it.filter { expenseDetailResponse -> !expenseDetailResponse.isIncome }
                        .map { item -> item.amount }.fold(0.0) { total, item -> total + item }
                _state.update { state ->
                    state.copy(income = inComes, expense = expenses)
                }
            }
        }
    }

    fun formatAmount(amount: Double): String {
        return amount.toCurrency(locale = localeHelper.getLocale())
    }

    private fun fetchExpenses() {
        viewModelScope.launch {
            when (val response = allExpensesUseCase.excuse()) {
                is NetworkResult.Error -> {

                }

                is NetworkResult.Loading -> {

                }

                is NetworkResult.Success -> {

                }
            }
        }
    }
}
