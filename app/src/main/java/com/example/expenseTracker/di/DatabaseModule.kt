package com.example.expenseTracker.di

import android.content.Context
import com.example.expenseTracker.data.database.UserDatabase
import com.example.expenseTracker.data.database.dao.ExampleDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideUserDatabase(
        @ApplicationContext context: Context,
    ): UserDatabase = UserDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideFavImageDao(userDatabase: UserDatabase): ExampleDAO = userDatabase.exampleDao()
}
