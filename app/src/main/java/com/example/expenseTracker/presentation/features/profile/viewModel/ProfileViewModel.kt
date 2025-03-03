package com.example.expenseTracker.presentation.features.profile.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.domain.usecase.users.UserInfoUseCase
import com.example.expenseTracker.domain.usecase.users.UserLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    init {
        observe()
    }

    private fun observe() {
        viewModelScope.launch {
            userInfoUseCase.excuse().collect { userInfo ->
                Log.d("ProfileViewModel", "userInfo: $userInfo")
                _state.update {
                    it.copy(email = userInfo?.email ?: "", name = userInfo?.name ?: "")
                }
            }
        }
    }
}