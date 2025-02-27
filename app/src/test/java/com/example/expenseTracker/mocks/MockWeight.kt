package com.example.expenseTracker.mocks

import com.example.expenseTracker.data.models.catData.BreedWeight

data class MockWeight(
    val imperial: String = "23",
    val metric: String = "25",
)

fun toResponseCatBreedWeight(mockWeight: MockWeight): BreedWeight = BreedWeight(mockWeight.imperial, mockWeight.metric)
