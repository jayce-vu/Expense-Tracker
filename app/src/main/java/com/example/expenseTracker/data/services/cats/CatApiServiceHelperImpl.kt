package com.example.expenseTracker.data.services.cats

import com.example.expenseTracker.data.models.catData.CatResponse
import com.example.expenseTracker.data.models.catData.FavouriteCatsItem
import com.example.expenseTracker.data.services.CatsService
import retrofit2.Response

class CatApiServiceHelperImpl(
    private val service: CatsService,
) : CatApiServiceHelper {
    override suspend fun fetchCatsImages(limit: Int): Response<List<CatResponse>> = service.fetchCatsImages(limit)

    override suspend fun fetchFavouriteCats(subId: String): Response<List<FavouriteCatsItem>> = service.fetchFavouriteCats(subId)
}
