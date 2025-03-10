package com.example.expenseTracker.data.repositories

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.services.ExpenseService
import com.example.expenseTracker.data.services.postModel.ExpenseUpdateRequest
import com.example.expenseTracker.data.services.postModel.PostExpense
import com.example.expenseTracker.data.services.responseModels.ExpenseDetailResponse
import com.example.expenseTracker.domain.repositories.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(private val expenseService: ExpenseService) :
    ExpenseRepository {
        private var mExpenses = MutableStateFlow<List<ExpenseDetailResponse>>(emptyList())
    override suspend fun getAllExpenses(): NetworkResult<List<ExpenseDetailResponse>> {
        val response = expenseService.getAllExpenses()
        if (response.isSuccessful) {
            val result = response.body()
            result?.let { nonNullResult ->
                if (nonNullResult.isSuccess()) {
                    mExpenses.update {
                        nonNullResult.data
                    }
                    return (NetworkResult.Success(nonNullResult.data))
                } else {
                    return (NetworkResult.Error(nonNullResult.message))
                }
            }
        }
        return (NetworkResult.Error(response.errorBody().toString()))
    }

    override suspend fun createExpense(postExpense: PostExpense): NetworkResult<Boolean> {
        val invoiceParts = postExpense.invoices.map { file ->
            val requestFile = file.asRequestBody("*/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("invoices", file.name, requestFile)
        }

        val response = expenseService.createExpense(
            categoryId = createPartFromString(postExpense.categoryId),
            amount = createPartFromString(postExpense.amount.toString()),
            description = createPartFromString(postExpense.description),
            date = createPartFromString(postExpense.date),
            invoices = invoiceParts,
            categoryName = createPartFromString(postExpense.categoryName),
            isIncome = createPartFromString(postExpense.isIncome.toString())
        )
        if (response.isSuccessful) {
            val result = response.body()
            result?.let { nonNullResult ->
                if (nonNullResult.isSuccess()) {
                    val item = nonNullResult.data
                    mExpenses.update {
                        it + listOf(item)
                    }
                    return (NetworkResult.Success(true))
                } else {
                    return (NetworkResult.Error(nonNullResult.message))
                }
            }
        }
        return (NetworkResult.Error(response.errorBody().toString()))
    }

    override suspend fun getExpenseById(id: String): NetworkResult<ExpenseDetailResponse> {
        val response = expenseService.getExpenseById(id)
        if (response.isSuccessful) {
            val result = response.body()
            result?.let { nonNullResult ->
                if (nonNullResult.isSuccess()) {
                    return (NetworkResult.Success(nonNullResult.data))
                } else {
                    return (NetworkResult.Error(nonNullResult.message))
                }
            }
        }
        return (NetworkResult.Error(response.errorBody().toString()))
    }

    override suspend fun updateExpense(
        id: String,
        expenseUpdateRequest: ExpenseUpdateRequest
    ): NetworkResult<Boolean> {
        val invoiceParts = expenseUpdateRequest.invoices.map { file ->
            val requestFile = file.asRequestBody("*/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("invoices", file.name, requestFile)
        }

        val response = expenseService.updateExpense(
            id = id,
            categoryId = createPartFromString(expenseUpdateRequest.categoryId),
            amount = createPartFromString(expenseUpdateRequest.amount.toString()),
            description = createPartFromString(expenseUpdateRequest.description),
            date = createPartFromString(expenseUpdateRequest.date),
            invoices = invoiceParts
        )
        if (response.isSuccessful) {
            val result = response.body()
            result?.let { nonNullResult ->
                if (nonNullResult.isSuccess()) {
                    return (NetworkResult.Success(true))
                } else {
                    return (NetworkResult.Error(nonNullResult.message))
                }
            }
        }
        return (NetworkResult.Error(response.errorBody().toString()))
    }

    override suspend fun deleteExpense(id: String): NetworkResult<Boolean> {
        val response = expenseService.deleteExpense(id)
        if (response.isSuccessful) {
            val result = response.body()
            result?.let { nonNullResult ->
                if (nonNullResult.isSuccess()) {
                    return (NetworkResult.Success(true))
                } else {
                    return (NetworkResult.Error(nonNullResult.message))
                }
            }
        }
        return (NetworkResult.Error(response.errorBody().toString()))
    }

    override fun getAllCachedExpenses(): StateFlow<List<ExpenseDetailResponse>> {
        return mExpenses
    }

    private fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}