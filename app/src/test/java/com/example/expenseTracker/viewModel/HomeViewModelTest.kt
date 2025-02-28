package com.example.expenseTracker.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.expenseTracker.data.models.catData.CatResponse
import com.example.expenseTracker.data.services.CatsService
import com.example.expenseTracker.domain.repositories.CatsRepository
import com.example.expenseTracker.mocks.MocksCatsDataModel
import com.example.expenseTracker.mocks.toResponseApiCats
import com.example.expenseTracker.mocks.toResponseCats
import com.example.expenseTracker.presentation.ui.features.home.viewModel.HomeViewModel
import com.example.expenseTracker.utils.LocaleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private lateinit var mCatsRepo: CatsRepository
    private lateinit var mViewModel: HomeViewModel

    @get:Rule
    val testInstantTaskExecutorRules: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var catService: CatsService

    @Mock
    lateinit var localeHelper: LocaleHelper

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        mViewModel = HomeViewModel(localeHelper)
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

        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
