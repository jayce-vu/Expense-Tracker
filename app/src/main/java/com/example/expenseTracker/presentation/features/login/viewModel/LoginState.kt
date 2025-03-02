package com.example.expenseTracker.presentation.features.login.viewModel

sealed class LoginState
data object LoginInitState : LoginState()
data object LoginLoadingState : LoginState()
data object LoginSuccessState : LoginState()
data class LoginErrorState(val error: String?) : LoginState()
