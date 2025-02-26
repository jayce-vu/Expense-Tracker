package com.example.expenseTracker.data

import android.content.Context
import com.example.expenseTracker.BuildConfig
import com.pddstudio.preferences.encrypted.EncryptedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

class EncryptedPreferenceManager @Inject constructor(@ApplicationContext context: Context) {
    private val encryptedPrefs: EncryptedPreferences =
        EncryptedPreferences.Builder(context).withEncryptionPassword(BuildConfig.prefPassword).build()

    fun getLanguage(): String = encryptedPrefs.getString("language", "en") ?: "en"

    fun setLanguage(lang: String) {
        encryptedPrefs.edit().putString("language", lang).apply()
    }
}