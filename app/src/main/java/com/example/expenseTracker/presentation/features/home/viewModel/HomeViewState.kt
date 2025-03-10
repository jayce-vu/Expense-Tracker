package com.example.expenseTracker.presentation.features.home.viewModel

import androidx.lifecycle.ViewModel

data class HomeViewState(val income: Double, val expense: Double):ViewModel(){
    val balance: Double
        get() = income - expense


}