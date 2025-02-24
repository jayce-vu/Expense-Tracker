package com.example.expenseTracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expenseTracker.data.database.entities.ExampleEntity

@Dao
interface ExampleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavCatImageRelation(favImageEntity: ExampleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavCatImageRelation(favImageEntity: List<ExampleEntity>): List<Long>

    @Query("SELECT exampleId from example_details WHERE imageId=:imageId")
    suspend fun getFavId(imageId: String?): Int?

    @Query("DELETE FROM example_details where imageId=:imgId")
    suspend fun deleteFavImage(imgId: String): Int

    @Query("DELETE FROM example_details")
    suspend fun clearTable(): Int
}
