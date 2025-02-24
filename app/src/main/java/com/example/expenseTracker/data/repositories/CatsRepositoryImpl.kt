package com.example.expenseTracker.data.repositories

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.models.catData.CatResponse
import com.example.expenseTracker.data.models.catData.FavouriteCatsItem
import com.example.expenseTracker.data.services.cats.CatApiServiceHelper
import com.example.expenseTracker.data.services.cats.CatsDatabaseHelper
import com.example.expenseTracker.domain.repositories.CatsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CatsRepositoryImpl(
    private val catsApiService: CatApiServiceHelper,
    private val catsDatabaseHelper: CatsDatabaseHelper,
) : CatsRepository {
    override suspend fun fetchCats(limit: Int) =
        flow<NetworkResult<List<CatResponse>>> {
            emit(NetworkResult.Loading())
            with(catsApiService.fetchCatsImages(limit)) {
                if (isSuccessful) {
                    emit(NetworkResult.Success(this.body()))
                } else {
                    emit(NetworkResult.Error(this.errorBody().toString()))
                }
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }

    override suspend fun fetchFavouriteCats(subId: String) =
        flow<NetworkResult<List<FavouriteCatsItem>>> {
            emit(NetworkResult.Loading())
            with(catsApiService.fetchFavouriteCats(subId)) {
                if (isSuccessful) {
                    emit(NetworkResult.Success(this.body()))
                    this.body()?.let { catsDatabaseHelper.insertFavCatImageRelation(it) }
                } else {
                    emit(NetworkResult.Error(this.errorBody().toString()))
                }
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }

    override suspend fun fetchTestFavouriteCats(subId: String) =
        flow<NetworkResult<List<FavouriteCatsItem>>> {
            emit(NetworkResult.Loading())
            with(catsApiService.fetchFavouriteCats(subId)) {
                if (isSuccessful) {
                    emit(NetworkResult.Success(this.body()))
                } else {
                    emit(NetworkResult.Error(this.errorBody().toString()))
                }
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }
}
