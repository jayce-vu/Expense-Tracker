package com.example.expenseTracker.di

import android.content.Context
import com.example.expenseTracker.data.preference.EncryptedPreferenceManager
import com.example.expenseTracker.data.preference.EncryptedPreferenceManagerImpl
import com.example.expenseTracker.utils.LocaleHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    fun provideEncryptedPreferenceManager(
        @ApplicationContext context: Context
    ): EncryptedPreferenceManager {
        return EncryptedPreferenceManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideLocaleHelper(
        encryptedPreferenceManager: EncryptedPreferenceManager
    ): LocaleHelper {
        return LocaleHelper(encryptedPreferenceManager)
    }
}