package com.example.expenseTracker.presentation.features.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expenseTracker.data.services.responseModels.ExpenseResponseModel
import com.example.expenseTracker.presentation.features.home.viewModel.HomeViewModel
import com.example.expenseTracker.presentation.layouts.BaseScreen
import com.example.expenseTracker.presentation.navigation.NavigationScreens
import com.example.expenseTracker.presentation.theme.ExpenseTrackerTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val color = MaterialTheme.colorScheme
    val style = MaterialTheme.typography
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()
    val expenses by viewModel.expenseList.collectAsState()
    BaseScreen(
        title = "Home",
        showAppBar = false,
        disablePadding = false,
        navController = navController
    ) {
        Box(
            modifier = Modifier
                .padding(top = 150.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    ),
                    colors = CardColors(
                        containerColor = color.inversePrimary,
                        contentColor = color.onPrimary,
                        disabledContainerColor = color.background,
                        disabledContentColor = color.inversePrimary
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Column {
                                Text("Total balance", style = style.titleLarge)
                                Text("2000$")
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BalanceItem(
                                style,
                                color,
                                "Income",
                                viewModel.formatAmount(state.income),
                                Icons.Filled.KeyboardArrowDown
                            )
                            BalanceItem(
                                style,
                                color,
                                "Expense",
                                viewModel.formatAmount(state.expense),
                                Icons.Filled.KeyboardArrowUp
                            )
                        }
                    }
                }
                TransactionList(expenses,style) {
                    navController.navigate(NavigationScreens.TransactionHistory.screenRoute)
                }
            }
        }
    }
}

@Composable
fun TransactionList(
    expenses: List<ExpenseResponseModel>,
    style: Typography,
    onSeeAll: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Transactions", style = style.titleLarge)
            TextButton(onClick = onSeeAll) {
                Text("See all", style = style.titleMedium)
            }
        }

        LazyColumn {
            items(expenses.size) { index ->
                ExpenseItem(expenses[index], style)
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: ExpenseResponseModel, style: Typography) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(expense.categoryName, style = style.bodyLarge)
            Text(expense.date, style = style.bodyMedium, color = Color.Gray)
        }
        Text("-$${expense.amount}", style = style.bodyLarge, color = Color.Red)
    }
}

@Preview
@Composable
private fun PreviewTransactionList() {
    Surface(modifier = Modifier.width(300.dp)) {
        TransactionList(emptyList(),MaterialTheme.typography) {}
    }
}

@Composable
private fun BalanceItem(
    style: Typography,
    color: ColorScheme,
    title: String,
    amount: String,
    icon: ImageVector
) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(size = 20.dp)
                    )
                    .background(color = color.secondary.copy(alpha = 0.3f)),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title
                )
            }

            Text(title, style = style.titleMedium)
        }
        Text(amount, style = style.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpenseTrackerTheme {
        HomeScreen(NavController(LocalContext.current))
    }
}
