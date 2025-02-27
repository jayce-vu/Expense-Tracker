package com.example.expenseTracker.presentation.ui.features.home.navigation

import com.example.expenseTracker.utils.Routes

sealed class NavigationScreens(
    var screenRoute: String,
) {
    data object Home : NavigationScreens(Routes.HOME_SCREEN)

    data object MyFavorites : NavigationScreens(Routes.MY_FAVOURITES_SCREEN)

    data object Login : NavigationScreens(Routes.LOGIN_SCREEN)
}