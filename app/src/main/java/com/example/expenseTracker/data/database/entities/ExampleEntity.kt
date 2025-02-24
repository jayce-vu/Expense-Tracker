package com.example.expenseTracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "example_details")
data class ExampleEntity(
    val exampleId: Int,
    @PrimaryKey
    val imageId: String,
)
