package com.example.expenseTracker.domain.repositories

interface LanguageRepository {
    fun getCurrentLanguage(): String
    fun setLanguage(lang: String)
}