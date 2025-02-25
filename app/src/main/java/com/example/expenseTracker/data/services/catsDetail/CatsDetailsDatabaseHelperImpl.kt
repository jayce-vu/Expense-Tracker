package com.example.expenseTracker.data.services.catsDetail

import com.example.expenseTracker.data.database.UserDatabase
import com.example.expenseTracker.data.database.entities.ExampleEntity

class CatsDetailsDatabaseHelperImpl(
    private val db: UserDatabase,
) : CatsDetailsDatabaseHelper {
    override suspend fun insertFavCatImageRelation(
        favCatId: Int,
        imageId: String,
    ): Long =
        ExampleEntity(favCatId, imageId).let {
            db.exampleDao().insertFavCatImageRelation(it)
        }

    override suspend fun deleteFavImage(catImageId: String): Int = db.exampleDao().deleteFavImage(catImageId)

    override suspend fun isFavourite(catImageId: String): Int? = db.exampleDao().getFavId(catImageId)
}
