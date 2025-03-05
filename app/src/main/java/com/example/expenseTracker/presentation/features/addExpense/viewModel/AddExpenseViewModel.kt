package com.example.expenseTracker.presentation.features.addExpense.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.data.services.postModel.PostExpense
import com.example.expenseTracker.domain.usecase.expenses.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(private val addExpenseUseCase: AddExpenseUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<AddExpenseState>(AddExpenseInitialState)
    val state: StateFlow<AddExpenseState> = _state


    fun addExpense(postExpense: PostExpense) {
        viewModelScope.launch {
            addExpenseUseCase.excuse(postExpense)
        }
    }
}