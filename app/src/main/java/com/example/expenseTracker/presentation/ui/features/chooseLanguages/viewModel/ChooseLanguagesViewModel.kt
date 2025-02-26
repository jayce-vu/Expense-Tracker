package com.example.expenseTracker.presentation.ui.features.chooseLanguages.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseTracker.domain.usecase.LanguageUseCase
import com.example.expenseTracker.utils.LocaleHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val useCase: LanguageUseCase,
    private val localeHelper: LocaleHelper
) : ViewModel() {
    private val _language = MutableStateFlow(useCase.getLanguage())
    val language: StateFlow<String> = _language

    fun changeLanguage(newLang: String, context: Context) {
        viewModelScope.launch {
            useCase.setLanguage(newLang)
            localeHelper.updateBaseContext(context)
            _language.value = newLang
        }
    }

}