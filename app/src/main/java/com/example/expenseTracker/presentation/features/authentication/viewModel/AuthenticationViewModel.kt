package com.example.expenseTracker.presentation.features.authentication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.domain.usecase.users.UserLoginUseCase
import com.example.expenseTracker.domain.usecase.users.UserLogoutUseCase
import com.example.expenseTracker.network.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val loginUseCase: UserLoginUseCase,
    private val logoutUseCase: UserLogoutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<AuthenticationState>(LogoutState)
    val state: StateFlow<AuthenticationState> = _state
    val authenticateLogoutObserve = sessionManager.observeLogout()
    fun login(email: String, password: String) {
        _state.update {
            AuthenticatingState
        }
        viewModelScope.launch {
            loginUseCase.execute(email, password).let { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        _state.update {
                            LoggedInState
                        }
                    }

                    is NetworkResult.Error -> {
                        _state.update {
                            LoginErrorState(error = response.message)
                        }
                    }

                    is NetworkResult.Loading -> {
                        _state.update {
                            AuthenticatingState
                        }
                    }
                }

            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            logoutUseCase.excuse()
        }
    }
}