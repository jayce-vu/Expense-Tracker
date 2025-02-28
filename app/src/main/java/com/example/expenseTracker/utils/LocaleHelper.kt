package com.example.expenseTracker.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.expenseTracker.data.preference.EncryptedPreferenceManager
import java.util.Locale
import javax.inject.Inject

class LocaleHelper @Inject constructor(
    private val encryptedPreferenceManager: EncryptedPreferenceManager
) {

    fun getCurrentLanguage(): String {
        return encryptedPreferenceManager.getLanguage()
    }

    fun getLocale(): Locale {
        val language = encryptedPreferenceManager.getLanguage()
        return if(language == "vi"){
            Locale("vi","VN")
        } else {
            Locale("en","US")
        }
    }

    fun updateBaseContext(context: Context) {
        setLocale(context, getCurrentLanguage())
    }

    companion object {
        fun setLocale(context: Context, language: String) {
            //version >= 13
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.getSystemService(LocaleManager::class.java).applicationLocales =
                    LocaleList.forLanguageTags(language)
            } else {
                //version < 13
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
            }
        }
    }
}
