package com.example.expenseTracker.di

import com.example.expenseTracker.data.EncryptedPreferenceManager
import com.example.expenseTracker.data.repositories.CatDetailsRepositoryImpl
import com.example.expenseTracker.data.repositories.CatsRepositoryImpl
import com.example.expenseTracker.data.repositories.LanguageRepositoryImpl
import com.example.expenseTracker.data.services.cats.CatApiServiceHelper
import com.example.expenseTracker.data.services.cats.CatsDatabaseHelper
import com.example.expenseTracker.data.services.catsDetail.CatDetailsApiServiceHelper
import com.example.expenseTracker.data.services.catsDetail.CatsDetailsDatabaseHelper
import com.example.expenseTracker.domain.repositories.CatDetailsRepository
import com.example.expenseTracker.domain.repositories.CatsRepository
import com.example.expenseTracker.domain.repositories.LanguageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    fun provideCatRepository(
        catsApiService: CatApiServiceHelper,
        catsDatabaseHelper: CatsDatabaseHelper,
    ): CatsRepository = CatsRepositoryImpl(catsApiService, catsDatabaseHelper)

    @Provides
    fun provideCatDetailsRepository(
        catsApiService: CatDetailsApiServiceHelper,
        catsDatabaseHelper: CatsDetailsDatabaseHelper,
    ): CatDetailsRepository = CatDetailsRepositoryImpl(catsApiService, catsDatabaseHelper)

    @Provides
    fun provideLanguageRepository(
        encryptedPreferenceManager: EncryptedPreferenceManager
    ): LanguageRepository {
        return LanguageRepositoryImpl(encryptedPreferenceManager)
    }
}
