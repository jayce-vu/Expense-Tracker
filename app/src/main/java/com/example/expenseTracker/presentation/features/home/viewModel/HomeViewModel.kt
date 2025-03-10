package com.example.expenseTracker.presentation.features.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.responseModels.ExpenseResponseModel
import com.example.expenseTracker.domain.usecase.expenses.AllExpensesUseCase
import com.example.expenseTracker.utils.LocaleHelper
import com.example.expenseTracker.utils.extensions.toCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val localeHelper: LocaleHelper, private val allExpensesUseCase: AllExpensesUseCase) :
    ViewModel() {
    private val _state =
        MutableStateFlow(
            HomeViewState(
                income = 0.0,
                expense = 0.0,
            ),
        )
    val state: StateFlow<HomeViewState> = _state
    private val _expenseState = MutableStateFlow<List<ExpenseResponseModel>>(emptyList())
    val expenseList: StateFlow<List<ExpenseResponseModel>> = _expenseState

    init {
        _state.update {
            it.copy(income = 12000.0, expense = 6000.0)
        }
        fetchExpenses()
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
                    response.data?.let { nonNullData ->
                        _expenseState.update {
                            nonNullData.take(10)
                        }
                    }
                }
            }
        }
    }
}
