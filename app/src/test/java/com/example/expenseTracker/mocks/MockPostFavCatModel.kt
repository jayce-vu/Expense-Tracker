package com.example.expenseTracker.mocks

import com.example.expenseTracker.data.models.catDetails.PostFavCatModel
import com.example.expenseTracker.utils.Constants
import com.google.gson.annotations.SerializedName

data class MockPostFavCatModel(
    @SerializedName("image_id")
    val imageId: String = "mi5",
    @SerializedName("sub_id")
    val subId: String = Constants.SUB_ID,
)

fun toRequestPostFavCatData(mockPostFavCatModel: MockPostFavCatModel): PostFavCatModel =
    PostFavCatModel(mockPostFavCatModel.imageId, mockPostFavCatModel.subId)
