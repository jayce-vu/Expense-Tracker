package com.example.expenseTracker.data.services

import com.example.expenseTracker.data.models.BaseResponse
import com.example.expenseTracker.data.services.postModel.CategoryRequest
import com.example.expenseTracker.data.services.responseModels.CategoryResponse
import retrofit2.http.*

interface CategoryService {

    @POST("categories")
    suspend fun createCategory(@Body request: CategoryRequest): BaseResponse<CategoryResponse>

    @GET("categories")
    suspend fun getAllCategories(): BaseResponse<List<CategoryResponse>>

    @GET("categories/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): BaseResponse<CategoryResponse>

    @PUT("categories/{id}")
    suspend fun updateCategory(@Path("id") id: Int, @Body request: CategoryRequest): BaseResponse<CategoryResponse>

    @DELETE("categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): BaseResponse<Unit>
}
