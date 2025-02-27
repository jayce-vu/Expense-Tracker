package com.example.expenseTracker.presentation.ui.features.home.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.expenseTracker.R
import com.example.expenseTracker.utils.Routes

data class BottomNavigationItem(
    val title: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val screenRoute: String = "",
)

fun getBottomNavigationItems(context: Context): List<BottomNavigationItem> =
    listOf(
        BottomNavigationItem(
            title = context.getString(R.string.home),
            icon = Icons.Filled.Home,
            screenRoute = NavigationScreens.Home.screenRoute,
        ),
        BottomNavigationItem(
            title = context.getString(R.string.my_favorites),
            icon = Icons.Filled.Favorite,
            screenRoute = NavigationScreens.MyFavorites.screenRoute,
        ),
    )
