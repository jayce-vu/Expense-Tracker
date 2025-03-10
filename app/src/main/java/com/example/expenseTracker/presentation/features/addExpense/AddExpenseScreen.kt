package com.example.expenseTracker.presentation.features.addExpense

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.features.addExpense.components.InvoiceForm
import com.example.expenseTracker.presentation.features.addExpense.viewModel.AddExpenseErrorState
import com.example.expenseTracker.presentation.features.addExpense.viewModel.AddExpenseSuccessState
import com.example.expenseTracker.presentation.features.addExpense.viewModel.AddExpenseViewModel
import com.example.expenseTracker.presentation.features.signup.viewModel.SignUpErrorState
import com.example.expenseTracker.presentation.layouts.BaseScreen

@Composable
fun AddExpenseScreen(navController: NavController){
    val style = MaterialTheme.typography
    val viewModel = hiltViewModel<AddExpenseViewModel>()
    val state by viewModel.state.collectAsState()
    val categoriesState by viewModel.categoriesState.collectAsState()

    LaunchedEffect(categoriesState) {  // ✅ Forces recomposition when categoriesState changes
        println("Categories updated: ${categoriesState.size}")
    }

    LaunchedEffect(state) {
        if(state is AddExpenseSuccessState){
            navController.popBackStack()
        }
    }
    BaseScreen (title = "Add Expense", navController = navController){
        Box(modifier = Modifier.padding(it).padding(top = 100.dp)){
            if(state is AddExpenseErrorState){
                Text(
                    (state as AddExpenseErrorState).message,
                    style = style.titleMedium.copy(color = Color.Red)
                )
            }
            InvoiceForm(state, categoriesState){ data ->
                viewModel.addExpense(data)
            }
        }
    }
}