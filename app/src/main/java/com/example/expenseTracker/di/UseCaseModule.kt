package com.example.expenseTracker.di

import com.example.expenseTracker.domain.repositories.ExpenseRepository
import com.example.expenseTracker.domain.repositories.UserRepository
import com.example.expenseTracker.domain.usecase.expenses.AddExpenseUseCase
import com.example.expenseTracker.domain.usecase.expenses.AllExpensesUseCase
import com.example.expenseTracker.domain.usecase.users.UserInfoUseCase
import com.example.expenseTracker.domain.usecase.users.UserLoginUseCase
import com.example.expenseTracker.domain.usecase.users.UserLogoutUseCase
import com.example.expenseTracker.domain.usecase.users.UserSignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    // User use case
    @Provides
    fun provideUserLoginUseCase(userRepository: UserRepository): UserLoginUseCase =
        UserLoginUseCase(userRepository)

    @Provides
    fun provideSignUpUseCase(userRepository: UserRepository): UserSignUpUseCase =
        UserSignUpUseCase(userRepository)

    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository): UserInfoUseCase =
        UserInfoUseCase(userRepository)

    @Provides
    fun provideLogoutUseCase(userRepository: UserRepository): UserLogoutUseCase =
        UserLogoutUseCase(userRepository)

    // expense use case
    @Provides
    fun provideAddExpenseUseCase(expenseRepository: ExpenseRepository): AddExpenseUseCase =
        AddExpenseUseCase(expenseRepository)

    @Provides
    fun provideAllExpenseUseCase(expenseRepository: ExpenseRepository): AllExpensesUseCase =
        AllExpensesUseCase(expenseRepository)
}
