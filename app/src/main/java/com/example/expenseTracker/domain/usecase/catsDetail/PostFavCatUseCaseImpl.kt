package com.example.expenseTracker.domain.usecase.catsDetail

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.models.catDetails.PostFavCatModel
import com.example.expenseTracker.domain.mappers.CallSuccessModel
import com.example.expenseTracker.domain.mappers.mapSuccessData
import com.example.expenseTracker.domain.repositories.CatDetailsRepository
import com.example.expenseTracker.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostFavCatUseCaseImpl(
    private val catDetailsRepo: CatDetailsRepository,
) : PostFavCatUseCase {
    override suspend fun execute(imageId: String): Flow<NetworkResult<CallSuccessModel>> =
        flow {
            val favCat = PostFavCatModel(imageId, Constants.SUB_ID)
            catDetailsRepo.postFavouriteCat(favCat).collect { response ->
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
