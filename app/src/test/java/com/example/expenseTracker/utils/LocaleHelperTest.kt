package com.example.expenseTracker.utils

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import android.app.LocaleManager
import android.os.LocaleList
import com.example.expenseTracker.data.preference.EncryptedPreferenceManager
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocaleHelperTest {

    @Mock
    private lateinit var encryptedPreferenceManager: EncryptedPreferenceManager

    @Mock
    private lateinit var context: Context

    private lateinit var localeHelper: LocaleHelper

    @Before
    fun setUp() {
        localeHelper = LocaleHelper(encryptedPreferenceManager)
    }

    @Test
    fun `getCurrentLanguage returns stored language`() {
        // Arrange
        `when`(encryptedPreferenceManager.getLanguage()).thenReturn("en")

        // Act
        val language = localeHelper.getCurrentLanguage()

        // Assert
        assertEquals("en", language)
    }

    @Test
    fun `updateBaseContext calls setLocale with stored language`() {
        // Arrange
        `when`(encryptedPreferenceManager.getLanguage()).thenReturn("vi")

        // Act
        localeHelper.updateBaseContext(context)

        // Assert
        verify(encryptedPreferenceManager).getLanguage()
    }

    @Test
    fun `setLocale sets language for versions higher 12` () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Mock LocaleManager
            val localeManager = mock(LocaleManager::class.java)
            `when`(context.getSystemService(LocaleManager::class.java)).thenReturn(localeManager)

            // Act
            LocaleHelper.setLocale(context, "vi")

            // Assert
            verify(localeManager).applicationLocales = LocaleList.forLanguageTags("vi")
        }
    }

    @Test
    fun `setLocale sets language for versions below 13`() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            // Act
            LocaleHelper.setLocale(context, "en")

            // Assert
            assertEquals(LocaleListCompat.forLanguageTags("en"), AppCompatDelegate.getApplicationLocales())
        }
    }
}
