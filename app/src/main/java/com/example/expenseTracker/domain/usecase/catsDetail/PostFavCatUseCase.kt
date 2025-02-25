package com.example.expenseTracker.domain.usecase.catsDetail

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.domain.mappers.CallSuccessModel
import kotlinx.coroutines.flow.Flow

interface PostFavCatUseCase {
    suspend fun execute(imageId: String): Flow<NetworkResult<CallSuccessModel>>
}
