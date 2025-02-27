package com.example.expenseTracker.presentation.ui.features.getStarted

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.ui.layouts.BaseScreen

@Composable
fun GetStartedScreen(navController: NavController){
    BaseScreen(
        showAppBar = false,
        navController = navController
    ){
        Text("Get Started")
    }
}