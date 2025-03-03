package com.example.expenseTracker.presentation.navigation

import com.example.expenseTracker.utils.Routes

sealed class NavigationScreens(
    var screenRoute: String,
) {
    data object Home : NavigationScreens(Routes.HOME_SCREEN)

    data object MyFavorites : NavigationScreens(Routes.MY_FAVOURITES_SCREEN)

    data object Login : NavigationScreens(Routes.LOGIN_SCREEN)
    data object GetStarted : NavigationScreens(Routes.LOGIN_SCREEN)
    data object Profile : NavigationScreens(Routes.PROFILE_SCREEN)
    data object AddExpense : NavigationScreens(Routes.ADD_EXPENSE_SCREEN)
    data object TransactionHistory : NavigationScreens(Routes.TRANSACTION_HISTORY_SCREEN)
    data object LoginScreen : NavigationScreens(Routes.LOGIN_SCREEN)
    data object SignUpScreen : NavigationScreens(Routes.SIGN_UP_SCREEN)
}