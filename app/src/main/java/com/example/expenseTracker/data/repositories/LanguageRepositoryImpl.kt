package com.example.expenseTracker.data.repositories

import com.example.expenseTracker.data.EncryptedPreferenceManager
import com.example.expenseTracker.domain.repositories.LanguageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepositoryImpl @Inject constructor(private val encryptedPrefs: EncryptedPreferenceManager) :
    LanguageRepository {
    override fun getCurrentLanguage(): String = encryptedPrefs.getLanguage()
    override fun setLanguage(lang: String) = encryptedPrefs.setLanguage(lang)
}