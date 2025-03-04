package com.example.expenseTracker.presentation.features.addExpense.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expenseTracker.presentation.components.AppOutlinedTextField
import com.example.expenseTracker.presentation.components.buttons.AppButton
import com.example.expenseTracker.presentation.components.buttons.AppDateSelectorButton
import com.example.expenseTracker.presentation.components.buttons.AppDropdownButton
import com.example.expenseTracker.presentation.components.buttons.AttachFileButton
import java.util.Calendar

@Composable
fun InvoiceForm() {
    val context = LocalContext.current
    var amount by remember { mutableStateOf("48.00") }
    var date by remember { mutableStateOf("Tue, 22 Feb 2022") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Name Dropdown
            AppDropdownButton(
                options = listOf("Option 1", "Option 2", "Option 3"),
                selectedOption = null,
                itemTitle = {
                    it
                },
                onOptionSelected = {}
            )
            // Amount TextField
            AppOutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                keyboardType = KeyboardType.Decimal,
                label = "Amount"
            )

            // Date TextField with Calendar Icon
            AppDateSelectorButton(
                context = context,
                selectedDate = Calendar.getInstance().time,
                onDateSelected = {})

            // Invoice Section (Placeholder for adding invoice)
            AttachFileButton(
                title = "Add invoice",
                onFileSelected = { uri ->

                },
            )
            // Create Button
            AppButton(
                text = "Create",
                onClick = { /* Create logic */ },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInvoiceForm() {
    InvoiceForm()
}