package com.example.expenseTracker.domain.usecase.catsDetail

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.domain.mappers.CallSuccessModel
import com.example.expenseTracker.domain.mappers.mapSuccessData
import com.example.expenseTracker.domain.repositories.CatDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteFavCatUseCaseImpl(
    private val catDetailsRepo: CatDetailsRepository,
) : DeleteFavCatUseCase {
    override suspend fun execute(
        imageId: String,
        favId: Int,
    ): Flow<NetworkResult<CallSuccessModel>> =
        flow {
            catDetailsRepo.deleteFavouriteCat(imageId, favId).collect { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        emit(NetworkResult.Success(response.data?.mapSuccessData()))
                    }

                    is NetworkResult.Error -> {
                        emit(NetworkResult.Error(response.message))
                    }

                    is NetworkResult.Loading -> {
                        emit(NetworkResult.Loading())
                    }
                }
            }
        }
}
