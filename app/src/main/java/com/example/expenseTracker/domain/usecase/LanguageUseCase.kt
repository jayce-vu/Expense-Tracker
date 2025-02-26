package com.example.expenseTracker.domain.usecase

import com.example.expenseTracker.domain.repositories.LanguageRepository
import javax.inject.Inject

class LanguageUseCase @Inject constructor(private val repository: LanguageRepository) {
    fun getLanguage(): String = repository.getCurrentLanguage()
    fun setLanguage(lang: String) = repository.setLanguage(lang)
}