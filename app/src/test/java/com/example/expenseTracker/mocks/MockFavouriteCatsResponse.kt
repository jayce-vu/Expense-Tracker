package com.example.expenseTracker.mocks

import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.models.catData.FavouriteCatsItem
import com.example.expenseTracker.data.models.catData.Image
import com.example.expenseTracker.domain.mappers.CatDataModel
import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class MockFavouriteCatsResponse(
    @SerializedName("created_at")
    val createdAt: String = "2023-10-22T22:13:49.000Z",
    val id: Int = 232,
    val image: Image = toResponseFavCatImage(MockFavCatImage()),
    @SerializedName("image_id")
    val imageId: String = "5i",
    @SerializedName("sub_id")
    val subId: String = "my-user",
    @SerializedName("user_id")
    val userId: String = "4",
)

fun toResponseApiFavCats(mocksFavCatsDataModel: MockFavouriteCatsResponse): Response<List<FavouriteCatsItem>> =
    Response.success(
        listOf(
            FavouriteCatsItem(
                mocksFavCatsDataModel.createdAt,
                mocksFavCatsDataModel.id,
                mocksFavCatsDataModel.image,
                mocksFavCatsDataModel.imageId,
                mocksFavCatsDataModel.subId,
                mocksFavCatsDataModel.userId,
            ),
        ),
    )

fun toResponseFavCats(mocksFavCatsDataModel: MockFavouriteCatsResponse): NetworkResult<List<CatDataModel>> =
    NetworkResult.Success(
        listOf(
            CatDataModel(
                favId = mocksFavCatsDataModel.id,
                url = mocksFavCatsDataModel.image.url,
                imageId = mocksFavCatsDataModel.imageId,
            ),
        ),
    )

data class MockFavCatImage(
    val id: String = "5i",
    val url: String = "https://cdn2.thecatapi.com/images/5i4.jpg",
)

fun toResponseFavCatImage(mockFavCatImage: MockFavCatImage): Image = Image(mockFavCatImage.id, mockFavCatImage.url)
