package com.example.expenseTracker.domain.usecase.cats

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.domain.mappers.CatDataModel
import com.example.expenseTracker.domain.mappers.mapFavCatsDataItems
import com.example.expenseTracker.domain.repositories.CatsRepository
import com.example.expenseTracker.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavCatsUseCaseImpl(
    private val catRepo: CatsRepository,
) : GetFavCatsUseCase {
    override suspend fun execute(): Flow<NetworkResult<List<CatDataModel>>> =
        flow {
            catRepo.fetchFavouriteCats(Constants.SUB_ID).collect { favCat ->
                when (favCat) {
                    is NetworkResult.Success -> {
                        val catDataList =
                            favCat.data?.map { cat ->
                                cat.mapFavCatsDataItems()
                            }
                        emit(NetworkResult.Success(catDataList?.reversed()))
                    }

                    is NetworkResult.Error -> {
                        emit(NetworkResult.Error(favCat.message))
                    }

                    is NetworkResult.Loading -> {
                        emit(NetworkResult.Loading())
                    }
                }
            }
        }
}
