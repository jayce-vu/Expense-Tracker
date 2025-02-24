package com.example.expenseTracker.domain.mappers

import com.example.expenseTracker.data.models.SuccessResponse
import com.example.expenseTracker.data.models.catData.CatResponse
import com.example.expenseTracker.data.models.catData.FavouriteCatsItem

// CatData Mapper function used for Cat image listData at Cats
fun CatResponse.mapCatsDataItems(): CatDataModel =
    CatDataModel(
        name = this.breeds[0].name,
        origin = this.breeds[0].origin,
        imageId = this.id,
        url = this.url,
    )

fun FavouriteCatsItem.mapFavCatsDataItems(): CatDataModel =
    CatDataModel(
        favId = this.id,
        url = this.image.url,
        imageId = this.imageId,
    )

fun SuccessResponse.mapSuccessData(): CallSuccessModel =
    CallSuccessModel(
        successMessage = this.message,
        id = this.id,
    )
