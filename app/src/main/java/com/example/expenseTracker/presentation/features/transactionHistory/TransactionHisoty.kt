package com.example.expenseTracker.presentation.features.transactionHistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expenseTracker.data.services.responseModels.ExpenseDetailResponse
import com.example.expenseTracker.presentation.features.transactionHistory.viewModel.TransactionViewModel
import com.example.expenseTracker.presentation.layouts.BaseScreen
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entriesOf
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun TransactionHistoryScreen(navController: NavController) {
    val viewModel = hiltViewModel<TransactionViewModel>()
    val state by viewModel.expenses.collectAsState()
    var showExpenseMenu by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Day") } // Default view
    val expenses by viewModel.expenses.collectAsState()
    BaseScreen(
        title = "Transaction History",
        showAppBar = true,
        showBackButton = true,
        showBackground = false,
        navController = navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 16.dp)
        ) {

            // Period Filters
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Day", "Week", "Month", "Year").forEach { filter ->
                    Button(
                        onClick = { selectedFilter = filter },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedFilter == filter) Color.Blue else Color.Gray
                        )
                    ) {
                        Text(filter, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Expense Dropdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${
                        expenses.map { it.amount }.reduce { total, item ->
                            total + item
                        }
                    }",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Box(modifier = Modifier.weight(1f)) { }
                Box {
                    Button(
                        onClick = { showExpenseMenu = !showExpenseMenu },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp)),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.LightGray)
                    ) {
                        Text(text = "Expense ▼", color = Color.White)
                    }
                    DropdownMenu(
                        expanded = showExpenseMenu,
                        onDismissRequest = { showExpenseMenu = false }
                    ) {
                        DropdownMenuItem(text = { Text("Option 1") }, onClick = {})
                        DropdownMenuItem(text = { Text("Option 2") }, onClick = {})
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chart using Vico
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                key(expenses.size) {
                    ExpenseLineChart(expenses, selectedFilter)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Top Spending Section
            Text(
                text = "Top Spending",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Transaction Items

            LazyColumn {
                val groupedTransactions = state.groupBy { it.date } // Group transactions by date

                groupedTransactions.toSortedMap(compareByDescending { it })
                    .forEach { (date, transactions) ->
                        // Section Header for Date
                        item {
                            Text(
                                text = date,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }

                        // Transactions for that Date
                        items(transactions.size) { index ->
                            val item = transactions[index]
                            TransactionItem(
                                iconRes = painterResource(id = android.R.drawable.ic_dialog_info),
                                title = item.categoryName,
                                date = item.date,
                                amount = "-$${item.amount}"
                            )
                        }
                    }
            }
        }
    }
}


@Composable
fun ExpenseLineChart(expenses: List<ExpenseDetailResponse>, filter: String) {

    val filteredEntries = remember(expenses, filter) {
        val groupedExpenses = when (filter) {
            "Day" -> expenses.groupBy { it.date } // Group by exact date
            "Week" -> expenses.groupBy { getWeekStartDate(it.date) } // Group by week start date
            "Month" -> expenses.groupBy { it.date.substring(0, 7) } // Extract "YYYY-MM"
            "Year" -> expenses.groupBy { it.date.substring(0, 4) } // Extract "YYYY"
            else -> emptyMap()
        }

        // Convert grouped data into (X, Y) chart entries
        groupedExpenses.map { (key, items) ->
            val totalAmount = items.sumOf { it.amount } // Sum expenses per group
            key to totalAmount.toFloat()
        }.sortedBy { it.first } // Ensure chronological order
    }

    val chartEntryModelProducer = remember(filteredEntries) {
        val entries = filteredEntries.mapIndexed { index, (date, amount) ->
            index.toFloat() to amount // X = index, Y = amount
        }
        ChartEntryModelProducer(entriesOf(*entries.toTypedArray()))
    }

    Chart(
        chart = lineChart(
            lines = listOf(
                lineSpec(lineColor = Color(0xFF4CAF50), lineThickness = 2.dp)
            )
        ),
        model = chartEntryModelProducer.getModel(),
        startAxis = startAxis(valueFormatter = DecimalFormatAxisValueFormatter()),
        bottomAxis = bottomAxis(
            valueFormatter = { value, _ ->
                filteredEntries.getOrNull(value.toInt())?.first
                    ?: "" // Display date/week/month/year labels
            }
        ),
        modifier = Modifier.fillMaxSize()
    )
}

fun getWeekStartDate(dateStr: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = sdf.parse(dateStr) ?: return dateStr // Parse input date

    val calendar = Calendar.getInstance().apply {
        time = date
        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Set to start of the week (Monday)
    }

    return sdf.format(calendar.time) // Return formatted start of the week
}

@Composable
fun TransactionItem(
    iconRes: Any,
    title: String,
    date: String,
    amount: String,
    isTransfer: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = date, fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = amount,
                fontSize = 16.sp,
                color = Color.Red,
                textAlign = TextAlign.End
            )
        }
    }
}


@Preview
@Composable
private fun PreviewProfileScreen() {
    Surface() {
        TransactionHistoryScreen(NavController(LocalContext.current))
    }
}