package com.example.expenseTracker.presentation.ui.features.home.components

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.ui.features.home.navigation.getBottomNavigationItems

@Composable
fun NavigationBottomBar(
    context: Context,
    navigationSelectedItem: Int,
    navController: NavHostController
) {
    var navigationSelectedItem1 = navigationSelectedItem
    NavigationBar(
        containerColor = colorResource(id = R.color.colorPrimary),
        contentColor = colorResource(id = R.color.white),
    ) {
        // getting the list of bottom navigation items for our data class
        getBottomNavigationItems(context).forEachIndexed { index, navigationItem ->
            // iterating all items with their respective indexes
            NavigationBarItem(
                selected = index == navigationSelectedItem1,
                label = {
                    Text(
                        text = navigationItem.title,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white),
                    )
                },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.title,
                        modifier = Modifier.semantics { testTag = navigationItem.title },
                        tint =
                        if (index == navigationSelectedItem1) {
                            colorResource(id = R.color.colorPrimary)
                        } else {
                            colorResource(id = R.color.white)
                        },
                    )
                },
                onClick = {
                    navigationSelectedItem1 = index
                    navController.navigate(navigationItem.screenRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}