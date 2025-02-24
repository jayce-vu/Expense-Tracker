package com.example.expenseTracker.presentation.contracts

import com.example.expenseTracker.domain.mappers.CallSuccessModel

class CatDetailsContract {
    data class State(
        val postFavCatSuccess: CallSuccessModel?,
        val deleteFavCatSuccess: CallSuccessModel?,
    )
}
