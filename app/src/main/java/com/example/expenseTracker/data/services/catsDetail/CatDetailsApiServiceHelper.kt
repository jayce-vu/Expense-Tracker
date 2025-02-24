package com.example.expenseTracker.data.services.catsDetail

import com.example.expenseTracker.data.models.SuccessResponse
import com.example.expenseTracker.data.models.catDetails.PostFavCatModel
import retrofit2.Response

interface CatDetailsApiServiceHelper {
    suspend fun postFavouriteCat(favCat: PostFavCatModel): Response<SuccessResponse>

    suspend fun deleteFavouriteCat(favouriteId: Int): Response<SuccessResponse>
}
