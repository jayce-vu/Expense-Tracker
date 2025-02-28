package com.example.expenseTracker.presentation.ui.features.transactionHistory

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.ui.layouts.BaseScreen

@Composable
fun TransactionHistoryScreen(navController: NavController) {
    BaseScreen(
        title = "Transaction History",
        showAppBar = true,
        showBackButton = true,
        navController = navController
    ) {
        Text("Transaction History")
    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    Surface() {
        TransactionHistoryScreen(NavController(LocalContext.current))
    }
}