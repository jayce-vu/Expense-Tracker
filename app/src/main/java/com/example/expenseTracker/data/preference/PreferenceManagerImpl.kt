package com.example.expenseTracker.data.preference

import android.content.Context
import com.example.expenseTracker.utils.Constants
import com.pddstudio.preferences.encrypted.EncryptedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EncryptedPreferenceManagerImpl @Inject constructor(@ApplicationContext context: Context): EncryptedPreferenceManager {
    private val encryptedPrefs: EncryptedPreferences =
        EncryptedPreferences.Builder(context).withPreferenceName(Constants.SHARED_PREFERENCES)
            .withEncryptionPassword(Constants.PREF_PASSWORD).build()

    override fun getLanguage(): String = encryptedPrefs.getString("language", "en") ?: "en"

    override fun setLanguage(lang: String) {
        encryptedPrefs.edit().putString("language", lang).apply()
    }
}
