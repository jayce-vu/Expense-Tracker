package com.example.expenseTracker.presentation.features.getStarted

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.layouts.BaseScreen

@Composable
fun GetStartedScreen(navController: NavController){
    BaseScreen(
        showAppBar = false,
        navController = navController
    ){
        Text("Get Started")
    }
}