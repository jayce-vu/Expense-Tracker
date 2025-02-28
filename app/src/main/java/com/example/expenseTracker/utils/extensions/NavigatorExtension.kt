package com.example.expenseTracker.utils.extensions

import androidx.navigation.NavController

fun NavController.navigateAndReplace(route: String) {
    this.navigate(route) {
        popUpTo(currentDestination?.id ?: return@navigate) { inclusive = true }
    }
}