package com.example.expenseTracker.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.expenseTracker.mocks.MockPostFavCatModel
import com.example.expenseTracker.mocks.MockSuccessResponse
import com.example.expenseTracker.mocks.toRequestPostFavCatData
import com.example.expenseTracker.mocks.toResponsePostSuccess
import com.example.expenseTracker.data.database.UserDatabase
import com.example.expenseTracker.data.models.SuccessResponse
import com.example.expenseTracker.data.repositories.CatDetailsRepositoryImpl
import com.example.expenseTracker.data.services.CatsService
import com.example.expenseTracker.data.services.catsDetail.CatDetailsApiServiceHelperImpl
import com.example.expenseTracker.data.services.catsDetail.CatsDetailsDatabaseHelperImpl
import com.example.expenseTracker.domain.usecase.catsDetail.CheckFavouriteUseCaseImpl
import com.example.expenseTracker.domain.usecase.catsDetail.DeleteFavCatUseCaseImpl
import com.example.expenseTracker.domain.usecase.catsDetail.PostFavCatUseCaseImpl
import com.example.expenseTracker.presentation.ui.features.catDetails.viewModel.CatsDetailsViewModel
import com.example.expenseTracker.utils.TestTags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CatsDetailsViewModelTest {
    private lateinit var mViewModel: CatsDetailsViewModel

    @get:Rule
    val testInstantTaskExecuterRules: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var catService: CatsService

    private val testDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val databaseReference = mock(UserDatabase::class.java)
        val apiHelper = CatDetailsApiServiceHelperImpl(catService)
        val dbHelper = CatsDetailsDatabaseHelperImpl(databaseReference)
        val mCatsRepo = CatDetailsRepositoryImpl(apiHelper, dbHelper)
        Dispatchers.setMain(testDispatcher)
        val postCatUseCase = PostFavCatUseCaseImpl(mCatsRepo)
        val deleteFavCatUseCase = DeleteFavCatUseCaseImpl(mCatsRepo)
        val checkFavCatUseCase = CheckFavouriteUseCaseImpl(mCatsRepo)

        mViewModel = CatsDetailsViewModel(postCatUseCase, deleteFavCatUseCase, checkFavCatUseCase)
    }

    @Test
    fun `test postFavCatData success`() =
        runTest(UnconfinedTestDispatcher()) {
            val postFavCatModel = toRequestPostFavCatData(MockPostFavCatModel())
            val expectedResponse = toResponsePostSuccess(MockSuccessResponse())
            Mockito.`when`(catService.postFavouriteCat(postFavCatModel)).thenReturn(expectedResponse)
            // Perform the actual request
            val response = catService.postFavouriteCat(postFavCatModel)
            // Assert the response
            assert(response.isSuccessful)
            assert(response.code() == 200)
            assert(response.body() == expectedResponse.body())
        }

    @Test
    fun `test deleteFavCat success`() =
        runTest(UnconfinedTestDispatcher()) {
            val expectedResponse =
                Response.success(SuccessResponse(0, message = "SUCCESS")) // HTTP status 204 for success
            Mockito.`when`(catService.deleteFavouriteCat(TestTags.FAV_ID)).thenReturn(expectedResponse)
            // Perform the actual request
            val response = catService.deleteFavouriteCat(TestTags.FAV_ID)
            // Assert the response
            assert(response.isSuccessful)
            assert(response.code() == 200) // HTTP status 204 indicates success
            assert(response.body() == expectedResponse.body())
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }
}
