package com.example.expenseTracker.presentation.ui.features.addExpense

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.ui.layouts.BaseScreen

@Composable
fun AddExpenseScreen(navController: NavController){
    BaseScreen (title = "Add Expense", navController = navController){
        Box(modifier = Modifier.padding(it)){
            Text("Add Expense Screen")
        }
    }
}