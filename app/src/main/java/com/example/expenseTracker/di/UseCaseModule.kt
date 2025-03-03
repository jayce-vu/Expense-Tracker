package com.example.expenseTracker.di

import com.example.expenseTracker.domain.repositories.UserRepository
import com.example.expenseTracker.domain.usecase.users.UserLoginUseCase
import com.example.expenseTracker.domain.usecase.users.UserSignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideUserLoginUseCase(userRepository: UserRepository): UserLoginUseCase = UserLoginUseCase(userRepository)

    @Provides
    fun provideSignUpUseCase(userRepository: UserRepository): UserSignUpUseCase = UserSignUpUseCase(userRepository)
}
