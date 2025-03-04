package com.example.expenseTracker.presentation.features.addExpense

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.features.addExpense.components.InvoiceForm
import com.example.expenseTracker.presentation.layouts.BaseScreen

@Composable
fun AddExpenseScreen(navController: NavController){
    BaseScreen (title = "Add Expense", navController = navController){
        Box(modifier = Modifier.padding(it).padding(top = 100.dp)){
            InvoiceForm()
        }
    }
}