package com.example.expenseTracker.presentation.features.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {

    private val _state = MutableStateFlow<LoginState>( LoginInitState)
    val state: StateFlow<LoginState> = _state

    fun login(email: String, password: String){
        viewModelScope.launch {
            _state.update {
                LoginLoadingState
            }
            userUseCase.execute(email, password).let{ response ->
                when (response){
                    is NetworkResult.Success -> {
                        _state.update {
                            LoginSuccessState
                        }
                    }
                    is NetworkResult.Error -> {
                        _state.update {
                            LoginErrorState(error= response.message)
                        }
                    }
                    is NetworkResult.Loading -> {
                        _state.update {
                            LoginLoadingState
                        }
                    }
                }

            }
        }
    }
}