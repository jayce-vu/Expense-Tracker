package com.example.expenseTracker.presentation.features.signup.viewModel

sealed class SignUpState
data object SignUpInitState : SignUpState()
data object SignUpLoadingState : SignUpState()
data object SignUpSuccessState : SignUpState()
data class SignUpErrorState(val error: String?) : SignUpState()
