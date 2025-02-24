package com.example.expenseTracker.data.services.cats

import com.example.expenseTracker.data.models.catData.CatResponse
import com.example.expenseTracker.data.models.catData.FavouriteCatsItem
import retrofit2.Response

interface CatApiServiceHelper {
    suspend fun fetchCatsImages(limit: Int): Response<List<CatResponse>>

    suspend fun fetchFavouriteCats(subId: String): Response<List<FavouriteCatsItem>>
}
