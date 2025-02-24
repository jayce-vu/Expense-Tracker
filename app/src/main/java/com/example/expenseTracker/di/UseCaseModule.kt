package com.example.expenseTracker.di

import com.example.expenseTracker.domain.repositories.CatDetailsRepository
import com.example.expenseTracker.domain.repositories.CatsRepository
import com.example.expenseTracker.domain.usecase.cats.GetCatsUseCase
import com.example.expenseTracker.domain.usecase.cats.GetCatsUseCaseImpl
import com.example.expenseTracker.domain.usecase.cats.GetFavCatsUseCase
import com.example.expenseTracker.domain.usecase.cats.GetFavCatsUseCaseImpl
import com.example.expenseTracker.domain.usecase.catsDetail.CheckFavUseCase
import com.example.expenseTracker.domain.usecase.catsDetail.CheckFavouriteUseCaseImpl
import com.example.expenseTracker.domain.usecase.catsDetail.DeleteFavCatUseCase
import com.example.expenseTracker.domain.usecase.catsDetail.DeleteFavCatUseCaseImpl
import com.example.expenseTracker.domain.usecase.catsDetail.PostFavCatUseCase
import com.example.expenseTracker.domain.usecase.catsDetail.PostFavCatUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideCatUseCase(catsRepo: CatsRepository): GetCatsUseCase = GetCatsUseCaseImpl(catsRepo)

    @Provides
    fun provideFavCatUseCase(catRepo: CatsRepository): GetFavCatsUseCase = GetFavCatsUseCaseImpl(catRepo)

    @Provides
    fun providePostFavCatUseCase(catDetailsRepo: CatDetailsRepository): PostFavCatUseCase = PostFavCatUseCaseImpl(catDetailsRepo)

    @Provides
    fun provideCheckFavCatUseCase(catDetailsRepo: CatDetailsRepository): CheckFavUseCase = CheckFavouriteUseCaseImpl(catDetailsRepo)

    @Provides
    fun provideDeleteFavCatUseCase(catDetailsRepo: CatDetailsRepository): DeleteFavCatUseCase = DeleteFavCatUseCaseImpl(catDetailsRepo)
}
