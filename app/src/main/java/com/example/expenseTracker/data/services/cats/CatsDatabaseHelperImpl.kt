package com.example.expenseTracker.data.services.cats

import com.example.expenseTracker.data.database.UserDatabase
import com.example.expenseTracker.data.database.entities.ExampleEntity
import com.example.expenseTracker.data.models.catData.FavouriteCatsItem

class CatsDatabaseHelperImpl(
    private val db: UserDatabase,
) : CatsDatabaseHelper {
    override suspend fun insertFavCatImageRelation(favCatItems: List<FavouriteCatsItem>): List<Long> {
        val favCatRelList =
            favCatItems.map {
                ExampleEntity(
                    exampleId = it.id,
                    imageId = it.imageId,
                )
            }
        return favCatRelList.let { db.exampleDao().insertFavCatImageRelation(favCatRelList) }
    }
}
