package com.example.expenseTracker.presentation.ui.features.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.domain.usecase.cats.GetCatsUseCase
import com.example.expenseTracker.domain.usecase.cats.GetFavCatsUseCase
import com.example.expenseTracker.presentation.contracts.BaseContract
import com.example.expenseTracker.presentation.contracts.CatContract
import com.example.expenseTracker.utils.ErrorsMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val catUseCase: GetCatsUseCase,
        private val getFavCatsUseCase: GetFavCatsUseCase,
    ) : ViewModel() {
        init {
            getCatsData()
            getFavCatsData()
        }

        private val _state =
            MutableStateFlow(
                CatContract.State(
                    cats = listOf(),
                    favCatsList = listOf(),
                    isLoading = true,
                ),
            )
        val state: StateFlow<CatContract.State> = _state

        var effects = Channel<BaseContract.Effect>(Channel.UNLIMITED)
            private set

        private fun updateState(newState: CatContract.State) {
            _state.value = newState
        }

        fun getFavCatsData() {
            viewModelScope.launch(Dispatchers.IO) {
                getFavCatsUseCase.execute().collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            val newState = _state.value.copy(favCatsList = it.data!!, isLoading = false)
                            updateState(newState)
                            effects.send(BaseContract.Effect.DataWasLoaded)
                        }

                        is NetworkResult.Error -> {
                            val newState = state.value.copy(isLoading = false)
                            updateState(newState)
                            effects.send(
                                BaseContract.Effect.Error(
                                    it.message ?: ErrorsMessage.GOT_API_CALL_ERROR,
                                ),
                            )
                        }

                        is NetworkResult.Loading -> {
                            if (!state.value.isLoading) {
                                val newState = state.value.copy(isLoading = true)
                                updateState(newState)
                            }
                        }
                    }
                }
            }
        }

        fun getCatsData() {
            viewModelScope.launch(Dispatchers.IO) {
                catUseCase.execute().collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            val newState = state.value.copy(cats = it.data!!, isLoading = false)
                            updateState(newState)
                            effects.send(BaseContract.Effect.DataWasLoaded)
                        }

                        is NetworkResult.Error -> {
                            val newState = state.value.copy(isLoading = false)
                            updateState(newState)
                            effects.send(
                                BaseContract.Effect.Error(
                                    it.message ?: ErrorsMessage.GOT_API_CALL_ERROR,
                                ),
                            )
                        }

                        is NetworkResult.Loading -> {
                            if (!state.value.isLoading) {
                                val newState = state.value.copy(isLoading = true)
                                updateState(newState)
                            }
                        }
                    }
                }
            }
        }
    }
