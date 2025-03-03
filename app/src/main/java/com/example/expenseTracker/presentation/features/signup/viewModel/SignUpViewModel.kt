package com.example.expenseTracker.presentation.features.signup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.domain.usecase.users.UserSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userUseCase: UserSignUpUseCase): ViewModel() {

    private val _state = MutableStateFlow<SignUpState>( SignUpInitState)
    val state: StateFlow<SignUpState> = _state

    fun signup(email: String, name: String, password: String, verifyPassword: String){
        _state.update {
            SignUpLoadingState
        }
        if(password != verifyPassword){
            _state.update {
                SignUpErrorState(error= "Password does not match")
            }
            return
        }
        viewModelScope.launch {
            userUseCase.execute(email, password, name).let{ response ->
                when (response){
                    is NetworkResult.Success -> {
                        _state.update {
                            SignUpSuccessState
                        }
                    }
                    is NetworkResult.Error -> {
                        _state.update {
                            SignUpErrorState(error= response.message)
                        }
                    }
                    is NetworkResult.Loading -> {
                        _state.update {
                            SignUpLoadingState
                        }
                    }
                }

            }
        }
    }
}