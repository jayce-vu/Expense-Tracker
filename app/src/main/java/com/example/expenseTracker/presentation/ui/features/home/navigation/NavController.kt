package com.example.expenseTracker.presentation.ui.features.home.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expenseTracker.presentation.ui.features.home.view.UserView

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavController(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.Home.screenRoute,
//        modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
    ) {
        composable(NavigationScreens.Home.screenRoute) {
            UserView(
                isFavCatsCall = false,
                navController = navController,
                onNavigationRequested = { id, id1 ->
                    navController.navigate(NavigationScreens.MyFavorites.screenRoute)
                },
            )
        }
        composable(NavigationScreens.MyFavorites.screenRoute) {
            UserView(
                isFavCatsCall = true,
                navController = navController,
                onNavigationRequested = { id1, id2 ->
                    navController.navigate(NavigationScreens.Home.screenRoute)
                },
            )
        }
        composable(NavigationScreens.Login.screenRoute) {
            Scaffold (
                topBar = {
                    TopAppBar(
                        title = {
                            Text("HELLO")
                        }
                    )
                }
            ){
                Text(text = "Login")
            }
        }
    }
}