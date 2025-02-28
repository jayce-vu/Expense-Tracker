package com.example.expenseTracker.presentation.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expenseTracker.presentation.ui.features.addExpense.AddExpenseScreen
import com.example.expenseTracker.presentation.ui.features.getStarted.GetStartedScreen
import com.example.expenseTracker.presentation.ui.features.home.view.HomeScreen
import com.example.expenseTracker.presentation.ui.features.login.LoginScreen
import com.example.expenseTracker.presentation.ui.features.profile.ProfileScreen
import com.example.expenseTracker.presentation.ui.features.signup.SignUpScreen
import com.example.expenseTracker.presentation.ui.features.transactionHistory.TransactionHistoryScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavController(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.LoginScreen.screenRoute,
    ) {
        composable(NavigationScreens.Home.screenRoute) {
            HomeScreen(navController)
        }
        composable(NavigationScreens.Profile.screenRoute) {
            ProfileScreen(navController)
        }
        composable(NavigationScreens.AddExpense.screenRoute) {
            AddExpenseScreen(navController)
        }
        composable(NavigationScreens.GetStarted.screenRoute) {
            GetStartedScreen(navController)
        }
        composable(NavigationScreens.TransactionHistory.screenRoute) {
            TransactionHistoryScreen(navController)
        }
        composable(NavigationScreens.LoginScreen.screenRoute) {
            LoginScreen(navController)
        }
        composable(NavigationScreens.SignUpScreen.screenRoute) {
            SignUpScreen(navController)
        }
    }
}