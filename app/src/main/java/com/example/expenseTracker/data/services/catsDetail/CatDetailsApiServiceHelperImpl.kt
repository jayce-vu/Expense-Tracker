package com.example.expenseTracker.data.services.catsDetail

import com.example.expenseTracker.data.models.SuccessResponse
import com.example.expenseTracker.data.models.catDetails.PostFavCatModel
import com.example.expenseTracker.data.services.CatsService
import retrofit2.Response

class CatDetailsApiServiceHelperImpl(
    private val service: CatsService,
) : CatDetailsApiServiceHelper {
    override suspend fun postFavouriteCat(favCat: PostFavCatModel): Response<SuccessResponse> = service.postFavouriteCat(favCat)

    override suspend fun deleteFavouriteCat(favouriteId: Int): Response<SuccessResponse> = service.deleteFavouriteCat(favouriteId)
}
