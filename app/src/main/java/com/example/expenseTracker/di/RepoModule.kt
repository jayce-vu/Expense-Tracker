package com.example.expenseTracker.di

import com.example.expenseTracker.data.preference.EncryptedPreferenceManager
import com.example.expenseTracker.data.repositories.LanguageRepositoryImpl
import com.example.expenseTracker.data.repositories.UserRepositoryImpl
import com.example.expenseTracker.data.services.UserService
import com.example.expenseTracker.domain.repositories.LanguageRepository
import com.example.expenseTracker.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        userService: UserService,
    ): UserRepository = UserRepositoryImpl(userService)

    @Singleton
    @Provides
    fun provideLanguageRepository(
        encryptedPreferenceManager: EncryptedPreferenceManager
    ): LanguageRepository {
        return LanguageRepositoryImpl(encryptedPreferenceManager)
    }
}
