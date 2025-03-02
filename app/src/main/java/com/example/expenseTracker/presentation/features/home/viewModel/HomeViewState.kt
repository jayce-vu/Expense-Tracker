package com.example.expenseTracker.presentation.features.home.viewModel

data class HomeViewState(val income: Double, val expense: Double){
    val balance: Double
        get() = income - expense

}