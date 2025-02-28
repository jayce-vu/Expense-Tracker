package com.example.expenseTracker.presentation.ui.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.ui.navigation.components.CustomBottomNavItemData
import com.example.expenseTracker.presentation.ui.navigation.components.CustomBottomNavigation


@Composable
fun CommonNavigationBar(
    modifier: Modifier = Modifier,
    context: Context,
    navigationSelectedItem: Int,
    navController: NavHostController,
    onItemClick: (Int) -> Unit = {}
) {

    CustomBottomNavigation(
        modifier = modifier,
        hideLabel = true,
        fabIcon = Icons.Filled.Add,
        onFabClick = {
            navController.navigate(NavigationScreens.AddExpense.screenRoute)
        },
        buttons = listOf(
            CustomBottomNavItemData(
                label = "Home",
                selected = navigationSelectedItem == 0,
                drawableSelectedResId = R.drawable.nav_home_filled,
                drawableUnselectedResId = R.drawable.nav_home_outline,
                onClick = {
                    onItemClick.invoke(0)
                    navController.navigate(NavigationScreens.Home.screenRoute)
                }
            ),
            CustomBottomNavItemData(
                label = "Profile",
                selected = navigationSelectedItem == 1,
                drawableSelectedResId = R.drawable.nav_profile_filled,
                drawableUnselectedResId = R.drawable.nav_profile_outline,
                onClick = {
                    onItemClick.invoke(1)
                    navController.navigate(NavigationScreens.Profile.screenRoute)
                }
            ),
        )
    )
}