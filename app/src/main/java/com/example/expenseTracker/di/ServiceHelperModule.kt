package com.example.expenseTracker.di

import com.example.expenseTracker.data.database.UserDatabase
import com.example.expenseTracker.data.services.CatsService
import com.example.expenseTracker.data.services.cats.CatApiServiceHelper
import com.example.expenseTracker.data.services.cats.CatApiServiceHelperImpl
import com.example.expenseTracker.data.services.cats.CatsDatabaseHelper
import com.example.expenseTracker.data.services.cats.CatsDatabaseHelperImpl
import com.example.expenseTracker.data.services.catsDetail.CatDetailsApiServiceHelper
import com.example.expenseTracker.data.services.catsDetail.CatDetailsApiServiceHelperImpl
import com.example.expenseTracker.data.services.catsDetail.CatsDetailsDatabaseHelper
import com.example.expenseTracker.data.services.catsDetail.CatsDetailsDatabaseHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ServiceHelperModule {
    @Provides
    fun provideCatApiServiceHelper(apiService: CatsService): CatApiServiceHelper = CatApiServiceHelperImpl(apiService)

    @Provides
    fun provideCatsDatabaseHelper(databaseHelper: UserDatabase): CatsDatabaseHelper = CatsDatabaseHelperImpl(databaseHelper)

    @Provides
    fun provideCatDetailsApiServiceHelper(apiService: CatsService): CatDetailsApiServiceHelper = CatDetailsApiServiceHelperImpl(apiService)

    @Provides
    fun provideCatsDetailsDatabaseHelper(databaseHelper: UserDatabase): CatsDetailsDatabaseHelper =
        CatsDetailsDatabaseHelperImpl(databaseHelper)
}
