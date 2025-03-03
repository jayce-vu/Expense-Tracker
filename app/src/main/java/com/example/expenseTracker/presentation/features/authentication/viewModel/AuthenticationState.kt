package com.example.expenseTracker.presentation.features.authentication.viewModel

abstract class AuthenticationState
object LogoutState : AuthenticationState()
object AuthenticatingState : AuthenticationState()
object LoggedInState : AuthenticationState()
data class LoginErrorState(val error: String?) : AuthenticationState()