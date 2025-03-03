package com.example.expenseTracker.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expenseTracker.presentation.features.addExpense.AddExpenseScreen
import com.example.expenseTracker.presentation.features.authentication.viewModel.AuthenticationViewModel
import com.example.expenseTracker.presentation.features.getStarted.GetStartedScreen
import com.example.expenseTracker.presentation.features.home.view.HomeScreen
import com.example.expenseTracker.presentation.features.login.LoginScreen
import com.example.expenseTracker.presentation.features.profile.ProfileScreen
import com.example.expenseTracker.presentation.features.signup.SignUpScreen
import com.example.expenseTracker.presentation.features.transactionHistory.TransactionHistoryScreen
import com.example.expenseTracker.utils.extensions.navigateAndReplace

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavController(navController: NavHostController, paddingValues: PaddingValues) {
    val authenticateViewModel = hiltViewModel<AuthenticationViewModel>()
    LaunchedEffect(authenticateViewModel.authenticateLogoutObserve) {
        authenticateViewModel.authenticateLogoutObserve.collect {
            navController.navigateAndReplace(NavigationScreens.LoginScreen.screenRoute)
        }
    }
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