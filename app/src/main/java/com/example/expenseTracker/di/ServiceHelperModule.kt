package com.example.expenseTracker.di

import com.example.expenseTracker.data.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ServiceHelperModule {
//    @Provides
//    fun provideCatApiServiceHelper(apiService: CatsService): CatApiServiceHelper = CatApiServiceHelperImpl(apiService)
//
//    @Provides
//    fun provideCatsDatabaseHelper(databaseHelper: UserDatabase): CatsDatabaseHelper = CatsDatabaseHelperImpl(databaseHelper)
//
//    @Provides
//    fun provideCatDetailsApiServiceHelper(apiService: CatsService): CatDetailsApiServiceHelper = CatDetailsApiServiceHelperImpl(apiService)
//
//    @Provides
//    fun provideCatsDetailsDatabaseHelper(databaseHelper: UserDatabase): CatsDetailsDatabaseHelper =
//        CatsDetailsDatabaseHelperImpl(databaseHelper)
}
