package com.example.expenseTracker.di

import com.example.expenseTracker.domain.repositories.UserRepository
import com.example.expenseTracker.domain.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase = UserUseCase(userRepository)
}
