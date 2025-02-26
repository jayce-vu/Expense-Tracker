package com.example.expenseTracker.domain.repositories

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.models.SuccessResponse
import com.example.expenseTracker.data.models.catDetails.PostFavCatModel
import kotlinx.coroutines.flow.Flow

interface CatDetailsRepository {
    suspend fun postFavouriteCat(favCat: PostFavCatModel): Flow<NetworkResult<SuccessResponse>>

    suspend fun deleteFavouriteCat(
        imageId: String,
        favouriteId: Int,
    ): Flow<NetworkResult<SuccessResponse>>

    suspend fun isFavourite(imageId: String): Flow<Int?>
}
