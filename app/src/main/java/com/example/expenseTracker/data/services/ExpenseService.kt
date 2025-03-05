package com.example.expenseTracker.data.services

import com.example.expenseTracker.data.models.BaseResponse
import com.example.expenseTracker.data.models.UserInfoModel
import com.example.expenseTracker.data.services.postModel.PostLogin
import com.example.expenseTracker.data.services.postModel.PostSignup
import com.example.expenseTracker.data.services.responseModels.ExpenseDetailResponse
import com.example.expenseTracker.data.services.responseModels.ExpenseResponseModel
import com.example.expenseTracker.data.services.responseModels.LoginResponseModel
import com.example.expenseTracker.data.services.responseModels.SignupResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.time.LocalDateTime

interface ExpenseService {
    @GET("expenses")
    suspend fun getAllExpenses(): Response<BaseResponse<List<ExpenseResponseModel>>>

    @Multipart
    @POST("expenses")
    suspend fun createExpense(
        @Part("categoryId") categoryId: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part("description") description: RequestBody,
        @Part("date") date: RequestBody,
        @Part invoices: List<MultipartBody.Part> // Multiple file uploads
    ): Response<BaseResponse<Any>>

    @POST("expenses/{id}")
    suspend fun getExpenseById(
        @Path("id") id: String,
    ): Response<BaseResponse<ExpenseDetailResponse>>

    @PATCH("expenses/{id}")
    suspend fun updateExpense(
        @Part("categoryId") categoryId: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part("description") description: RequestBody,
        @Part("date") date: RequestBody,
        @Path("id") id: String,
        @Part invoices: List<MultipartBody.Part> // Multiple file uploads
    ): Response<BaseResponse<Any>>

    @DELETE("expenses/{id}")
    suspend fun deleteExpense(
        @Path("id") id: String
    ): Response<BaseResponse<Any>>
}