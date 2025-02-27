package com.example.expenseTracker.viewModel

import com.example.expenseTracker.data.preference.EncryptedPreferenceManager
import com.example.expenseTracker.data.repositories.LanguageRepositoryImpl
import com.example.expenseTracker.domain.repositories.LanguageRepository
import com.example.expenseTracker.domain.usecase.LanguageUseCase
import com.example.expenseTracker.mocks.EncryptPreferenceMock
import com.example.expenseTracker.presentation.ui.features.chooseLanguages.viewModel.LanguageViewModel
import com.example.expenseTracker.utils.LocaleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LanguageViewModelTest {
    private lateinit var mLanguageRepo: LanguageRepository
    private lateinit var mViewModel: LanguageViewModel
    private lateinit var mUseCase: LanguageUseCase
    private lateinit var encryptedPreferenceManager: EncryptedPreferenceManager

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        encryptedPreferenceManager = EncryptPreferenceMock()
        val localeHelper: LocaleHelper = mock()
        mLanguageRepo = LanguageRepositoryImpl(encryptedPreferenceManager)

        mUseCase = LanguageUseCase(mLanguageRepo)
        mViewModel = LanguageViewModel(mUseCase, localeHelper)
    }

    @Test
    fun `when init viewmodel, then check current language`() = runTest {
        val expectedLanguage = encryptedPreferenceManager.getLanguage()
        val stateResult = mViewModel.language.value
        assertEquals(expectedLanguage, stateResult)
    }

    @Test
    fun `when change language, then update language`() = runTest {
        val expectedLanguage = "vi"
        mViewModel.changeLanguage(expectedLanguage, mock())
        val stateResult = mViewModel.language.value
        assertEquals(expectedLanguage, stateResult)
        assertEquals(expectedLanguage, mUseCase.getLanguage())
        assertEquals(expectedLanguage, mLanguageRepo.getCurrentLanguage())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
