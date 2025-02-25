package com.example.expenseTracker.viewModel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.expenseTracker.R
import com.example.expenseTracker.catMocks.*
import com.example.expenseTracker.data.NetworkResult
import com.example.expenseTracker.data.database.UserDatabase
import com.example.expenseTracker.data.models.catData.CatResponse
import com.example.expenseTracker.data.models.catData.FavouriteCatsItem
import com.example.expenseTracker.data.repositories.CatsRepositoryImpl
import com.example.expenseTracker.data.services.CatsService
import com.example.expenseTracker.data.services.cats.CatApiServiceHelperImpl
import com.example.expenseTracker.data.services.cats.CatsDatabaseHelperImpl
import com.example.expenseTracker.domain.repositories.CatsRepository
import com.example.expenseTracker.domain.usecase.cats.GetCatsUseCaseImpl
import com.example.expenseTracker.domain.usecase.cats.GetFavCatsUseCaseImpl
import com.example.expenseTracker.presentation.ui.features.cats.viewModel.CatsViewModel
import com.example.expenseTracker.utils.Constants
import com.example.expenseTracker.utils.TestTags
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CatsViewModelTest {
    private lateinit var mCatsRepo: CatsRepository
    private lateinit var mViewModel: CatsViewModel

    @get:Rule
    val testInstantTaskExecutorRules: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var catService: CatsService

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        val databaseReference = mock(UserDatabase::class.java)
        val apiHelper = CatApiServiceHelperImpl(catService)
        val dbHelper = CatsDatabaseHelperImpl(databaseReference)
        mCatsRepo = CatsRepositoryImpl(apiHelper, dbHelper)

        val catUseCase = GetCatsUseCaseImpl(mCatsRepo)
        val favCatUseCase = GetFavCatsUseCaseImpl(mCatsRepo)

        mViewModel = CatsViewModel(catUseCase, favCatUseCase)
    }

    @Test
    fun `test get empty cats data`() = runTest {
        val expectedRepositories = Response.success(emptyList<CatResponse>())
        `when`(catService.fetchCatsImages(0)).thenReturn(expectedRepositories)

        val result = catService.fetchCatsImages(0)

        verify(catService).fetchCatsImages(0)
        assertEquals(expectedRepositories, result)
    }

    @Test
    fun testGetCatsApiData() =
        runTest(testDispatcher) {
            val mockCatsData = MocksCatsDataModel()
            val response = toResponseApiCats(mockCatsData)
            val verifyData = toResponseCats(mockCatsData)

            `when`(catService.fetchCatsImages(10)).thenReturn(response)
            verify(catService).fetchCatsImages(10)
            mViewModel.getCatsData()
            testDispatcher.scheduler.advanceUntilIdle()
            val result = mViewModel.state.value.cats
            assertEquals(verifyData.size, result.size)
            assertEquals(verifyData[0].url, result[0].url)
        }

    @Test
    fun `test get favourite empty data`() = runTest {
        val expectedRepositories = Response.success(emptyList<FavouriteCatsItem>())
        `when`(catService.fetchFavouriteCats("0")).thenReturn(expectedRepositories)

        val result = catService.fetchFavouriteCats("0")

        verify(catService).fetchFavouriteCats("0")
        assertEquals(expectedRepositories, result)
    }

    @Test
    fun `test fetch favourite cats success state`() = runTest {
        val mockCatsData = MockFavouriteCatsResponse()
        val apiResponse = toResponseApiFavCats(mockCatsData)
        val verifyData = toResponseFavCats(mockCatsData)

        whenever(catService.fetchFavouriteCats(Constants.SUB_ID)).thenReturn(apiResponse)

        val result = mCatsRepo.fetchTestFavouriteCats(Constants.SUB_ID).toList()

        assertTrue(result[1] is NetworkResult.Success)
        assertEquals(verifyData.data?.size, result[1].data?.size)
        assertEquals(verifyData.data?.get(0)?.url, result[1].data?.get(0)?.image?.url)
    }

    @Test
    fun `test fetch favourite cats error state`() = runTest {
        val errorResponse = Response.error<List<FavouriteCatsItem>>(
            400,
            "Error message".toResponseBody("application/json".toMediaType())
        )

        `when`(catService.fetchFavouriteCats(TestTags.SUB_ID)).thenReturn(errorResponse)

        val result = mCatsRepo.fetchFavouriteCats(TestTags.SUB_ID).toList()

        verify(catService).fetchFavouriteCats(TestTags.SUB_ID)
        assertTrue(result[1] is NetworkResult.Error)
    }

    @Test
    fun `test fetch favourite cats exception`() = runTest {
        `when`(catService.fetchFavouriteCats(TestTags.SUB_ID)).thenThrow(RuntimeException("An error occurred"))

        val result = mCatsRepo.fetchFavouriteCats(TestTags.SUB_ID).toList()

        verify(catService).fetchFavouriteCats(TestTags.SUB_ID)
        assertTrue(result[1] is NetworkResult.Error)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
