package com.example.expenseTracker.data.preference

import android.content.Context
import com.example.expenseTracker.utils.Constants
import com.pddstudio.preferences.encrypted.EncryptedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface EncryptedPreferenceManager{

    fun getLanguage(): String

    fun setLanguage(lang: String)
}
