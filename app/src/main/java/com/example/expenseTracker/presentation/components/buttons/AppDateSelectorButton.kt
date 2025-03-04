package com.example.expenseTracker.presentation.components.buttons

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AppDateSelectorButton(
    context: Context,
    selectedDate: Date,
    onDateSelected: (Date) -> Unit
) {
    var date by remember { mutableStateOf(selectedDate) }
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                date = calendar.time
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDatePicker() }
    ) {
        OutlinedTextField(
            value = dateFormat.format(date),
            onValueChange = {},
            enabled = false,
            label = { Text("DATE") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Sharp.DateRange,
                    contentDescription = "Calendar",
                    modifier = Modifier.clickable { showDatePicker() }
                )
            },
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.Black,
                disabledTrailingIconColor = Color.Black
            )
        )
    }
}

@Preview
@Composable
fun PreviewDateSelectorButton() {
    val context = LocalContext.current
    AppDateSelectorButton(context = context, selectedDate = Date(), onDateSelected = {})
}
