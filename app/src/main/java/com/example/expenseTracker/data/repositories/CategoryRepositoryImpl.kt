package com.example.expenseTracker.data.repositories

import android.util.Log
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.models.BaseResponse
import com.example.expenseTracker.data.services.CategoryService
import com.example.expenseTracker.data.services.postModel.CategoryRequest
import com.example.expenseTracker.data.services.responseModels.CategoryResponse
import com.example.expenseTracker.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(private val categoryService: CategoryService): CategoryRepository {

    private var mCachedCategories = MutableStateFlow<List<CategoryResponse>> (emptyList())

    override suspend fun createCategory(request: CategoryRequest): NetworkResult<CategoryResponse> {
        return safeApiCall { categoryService.createCategory(request) }
    }

    override suspend fun getAllCategories(): NetworkResult<List<CategoryResponse>> {
        val result = safeApiCall { categoryService.getAllCategories() }
        Log.d("CategoryRepositoryImpl", "getAllCategories: $result")
        if(result is NetworkResult.Success){
            result.data?.let { nonNullData ->
                mCachedCategories.update {
                    nonNullData
                }
            }
        } else if (result is NetworkResult.Error){
            Log.d("CategoryRepositoryImpl", "getAllCategories ERROR: ${result.message}")
            mCachedCategories.update {
                emptyList()
            }
        }
        return result
    }

    override suspend fun getCategoryById(id: Int): NetworkResult<CategoryResponse> {
        return safeApiCall { categoryService.getCategoryById(id) }
    }

    override suspend fun updateCategory(id: Int, request: CategoryRequest): NetworkResult<CategoryResponse> {
        return safeApiCall { categoryService.updateCategory(id, request) }
    }

    override suspend fun deleteCategory(id: Int): NetworkResult<Unit> {
        return safeApiCall { categoryService.deleteCategory(id) }
    }

    override fun getCachedCategories(): StateFlow<List<CategoryResponse>> {
        return mCachedCategories.asStateFlow()
    }

    // Generic function to handle API calls safely
    private inline fun <T> safeApiCall(apiCall: () -> BaseResponse<T>): NetworkResult<T> {
        return try {
            val response = apiCall()
            if (response.status == "success") {
                NetworkResult.Success(response.data)
            } else {
                NetworkResult.Error(response.message)
            }
        } catch (e: HttpException) {
            NetworkResult.Error("HTTP Exception: ${e.message()}")
        } catch (e: IOException) {
            NetworkResult.Error("Network Error: Please check your connection.")
        } catch (e: Exception) {
            NetworkResult.Error("Unknown Error: ${e.localizedMessage}")
        }
    }
}