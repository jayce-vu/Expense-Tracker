package com.example.expenseTracker.presentation.contracts

import com.example.expenseTracker.domain.mappers.CatDataModel

class CatContract {
    data class State(
        val cats: List<CatDataModel> = listOf(),
        val favCatsList: List<CatDataModel> = listOf(),
        val isLoading: Boolean = false,
    )
}
