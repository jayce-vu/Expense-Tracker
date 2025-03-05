package com.example.expenseTracker.presentation.features.addExpense.viewModel

abstract class AddExpenseState
object AddExpenseInitialState: AddExpenseState()
object AddExpenseLoadingState: AddExpenseState()
object AddExpenseSuccessState: AddExpenseState()
data class AddExpenseErrorState(val message: String): AddExpenseState()