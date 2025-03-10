package com.example.expenseTracker.domain.usecase.categories

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.postModel.CategoryRequest
import com.example.expenseTracker.data.services.responseModels.CategoryResponse
import kotlinx.coroutines.flow.StateFlow

interface ICategoryUseCase {
    suspend fun createCategory(request: CategoryRequest): NetworkResult<CategoryResponse>
    suspend fun getAllCategories(): NetworkResult<List<CategoryResponse>>
    suspend fun getCategoryById(id: Int): NetworkResult<CategoryResponse>
    suspend fun updateCategory(id: Int, request: CategoryRequest): NetworkResult<CategoryResponse>
    suspend fun deleteCategory(id: Int): NetworkResult<Unit>
    fun getCachedCategories(): StateFlow<List<CategoryResponse>>
}