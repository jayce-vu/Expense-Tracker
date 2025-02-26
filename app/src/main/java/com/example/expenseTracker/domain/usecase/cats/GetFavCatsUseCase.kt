package com.example.expenseTracker.domain.usecase.cats

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.domain.mappers.CatDataModel
import kotlinx.coroutines.flow.Flow

interface GetFavCatsUseCase {
    suspend fun execute(): Flow<NetworkResult<List<CatDataModel>>>
}
