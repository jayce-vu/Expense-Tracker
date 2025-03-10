package com.example.expenseTracker.di

import com.example.expenseTracker.data.preference.EncryptedPreferenceManager
import com.example.expenseTracker.data.repositories.CategoryRepositoryImpl
import com.example.expenseTracker.data.repositories.ExpenseRepositoryImpl
import com.example.expenseTracker.data.repositories.LanguageRepositoryImpl
import com.example.expenseTracker.data.repositories.UserRepositoryImpl
import com.example.expenseTracker.data.services.CategoryService
import com.example.expenseTracker.data.services.ExpenseService
import com.example.expenseTracker.data.services.UserService
import com.example.expenseTracker.domain.repositories.CategoryRepository
import com.example.expenseTracker.domain.repositories.ExpenseRepository
import com.example.expenseTracker.domain.repositories.LanguageRepository
import com.example.expenseTracker.domain.repositories.UserRepository
import com.example.expenseTracker.network.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        userService: UserService,
        sessionManager: SessionManager
    ): UserRepository = UserRepositoryImpl(userService, sessionManager)

    @Singleton
    @Provides
    fun provideLanguageRepository(
        encryptedPreferenceManager: EncryptedPreferenceManager
    ): LanguageRepository {
        return LanguageRepositoryImpl(encryptedPreferenceManager)
    }

    @Singleton
    @Provides
    fun provideExpenseRepository(
        expenseService: ExpenseService,
    ): ExpenseRepository = ExpenseRepositoryImpl(expenseService)

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryService: CategoryService) : CategoryRepository = CategoryRepositoryImpl(categoryService)
}
