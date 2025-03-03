package com.example.expenseTracker.presentation.features.home.viewModel

import androidx.lifecycle.ViewModel
import com.example.expenseTracker.utils.LocaleHelper
import com.example.expenseTracker.utils.extensions.toCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val localeHelper: LocaleHelper) : ViewModel() {
    private val _state =
        MutableStateFlow(
            HomeViewState(
                income = 0.0,
                expense = 0.0,
            ),
        )
    val state: StateFlow<HomeViewState> = _state

    init {
        _state.update {
            it.copy(income = 12000.0, expense = 6000.0)
        }
    }

    fun formatAmount(amount: Double): String {
        return amount.toCurrency(locale = localeHelper.getLocale())
    }
}
