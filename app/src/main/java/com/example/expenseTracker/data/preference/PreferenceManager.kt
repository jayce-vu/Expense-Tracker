package com.example.expenseTracker.data.preference

interface EncryptedPreferenceManager {

    fun getLanguage(): String

    fun setLanguage(lang: String)

    fun setToken(token: String)

    fun getToken(): String
}
