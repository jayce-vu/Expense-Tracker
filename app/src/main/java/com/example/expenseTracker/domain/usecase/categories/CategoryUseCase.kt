package com.example.expenseTracker.domain.usecase.categories

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.postModel.CategoryRequest
import com.example.expenseTracker.data.services.responseModels.CategoryResponse
import com.example.expenseTracker.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val repository: CategoryRepository) : ICategoryUseCase {

    override suspend fun createCategory(request: CategoryRequest): NetworkResult<CategoryResponse> {
        return repository.createCategory(request)
    }

    override suspend fun getAllCategories(): NetworkResult<List<CategoryResponse>> {
        return repository.getAllCategories()
    }

    override suspend fun getCategoryById(id: Int): NetworkResult<CategoryResponse> {
        return repository.getCategoryById(id)
    }

    override suspend fun updateCategory(id: Int, request: CategoryRequest): NetworkResult<CategoryResponse> {
        return repository.updateCategory(id, request)
    }

    override suspend fun deleteCategory(id: Int): NetworkResult<Unit> {
        return repository.deleteCategory(id)
    }

    override fun getCachedCategories(): StateFlow<List<CategoryResponse>> {
        return repository.getCachedCategories()
    }
}
