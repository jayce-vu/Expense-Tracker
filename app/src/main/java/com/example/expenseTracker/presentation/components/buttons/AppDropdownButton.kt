package com.example.expenseTracker.presentation.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun <T> AppDropdownButton(
    options: List<T?>,
    itemTitle: (T?) -> String?,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    label: String = "Select Option"
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(itemTitle(selectedOption)) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.clickable { expanded = true }) {
            OutlinedTextField(
                value = selectedText ?: label,
                onValueChange = {},
                enabled = false,
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = if (!expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = "Dropdown Arrow"
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledContainerColor = Color.White,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledTrailingIconColor = Color.Black
                )
            )
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth(),
            ) {
                DropdownMenu(
                    modifier = Modifier.width(with(LocalDensity.current) { maxWidth }),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    options.filterNot { it == null }.forEach { option ->
                        itemTitle(option)?.let { nonNullTitle ->
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(Icons.Default.ShoppingCart, null)
                                },
                                text = { Text(nonNullTitle) },
                                onClick = {
                                    selectedText = nonNullTitle
                                    expanded = false
                                    option?.let(onOptionSelected)
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewCommonDropdown() {
    AppDropdownButton(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedOption = "Option 1",
        itemTitle = {
            it
        },
        onOptionSelected = {}
    )
}
