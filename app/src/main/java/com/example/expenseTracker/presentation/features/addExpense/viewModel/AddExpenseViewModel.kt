package com.example.expenseTracker.presentation.features.addExpense.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.postModel.PostExpense
import com.example.expenseTracker.data.services.responseModels.CategoryResponse
import com.example.expenseTracker.domain.usecase.categories.CategoryUseCase
import com.example.expenseTracker.domain.usecase.categories.ICategoryUseCase
import com.example.expenseTracker.domain.usecase.expenses.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val categoryUseCase: ICategoryUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow<AddExpenseState>(AddExpenseInitialState)
    val state: StateFlow<AddExpenseState> = _state
    private val _categoriesState = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categoriesState: StateFlow<List<CategoryResponse>> = _categoriesState

    init {
        loadData()
        observe()
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = categoryUseCase.getAllCategories()
            if (result is NetworkResult.Success) {
                _categoriesState.update {
                    result.data ?: emptyList()
                }
                Log.d("AddExpenseViewModel", "final result: ${_categoriesState.value}")
            }
        }
    }

    private fun observe() {
        viewModelScope.launch {
            categoryUseCase.getCachedCategories().collect {
                _categoriesState.update { it }
            }
        }
    }

    fun addExpense(postExpense: PostExpense) {
        viewModelScope.launch {
            val result = addExpenseUseCase.excuse(postExpense)
            if (result.data == true) {
                _state.update {
                    AddExpenseSuccessState
                }
            } else {
                _state.update {
                    AddExpenseErrorState(result.message ?: "")
                }
            }
        }
    }
}