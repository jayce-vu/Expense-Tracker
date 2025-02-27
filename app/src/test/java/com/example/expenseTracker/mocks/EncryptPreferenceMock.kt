package com.example.expenseTracker.mocks

import com.example.expenseTracker.data.preference.EncryptedPreferenceManager

class EncryptPreferenceMock : EncryptedPreferenceManager {
    private var language = "en"
    override fun getLanguage(): String = language

    override fun setLanguage(lang: String) {
        language = lang
    }
}