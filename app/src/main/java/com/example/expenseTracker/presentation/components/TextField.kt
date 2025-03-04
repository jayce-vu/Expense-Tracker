package com.example.expenseTracker.presentation.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    regex: Regex? = null, // Optional validation regex
    errorMessage: String? = null // Custom error message
) {
    val isError = regex != null && value.isNotEmpty() && !regex.matches(value)
    val displayErrorMessage = if (isError) errorMessage else null

    var field by remember { mutableStateOf(value) }

    OutlinedTextField(value = field,
        onValueChange = {
            onValueChange.invoke(it)
            field = it
        },
        label = { Text(text = label, style = TextStyle(color = Color.Gray, fontSize = 14.sp)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = if (isError) 0.dp else 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
        visualTransformation = visualTransformation,
        isError = isError,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White, unfocusedContainerColor = Color.White
        ),
        supportingText = displayErrorMessage?.let {
            { Text(text = it, color = Color.Red) }
        },
        trailingIcon = {
            IconButton(onClick = { field = "" }) {
                Text("Clear", fontSize = 12.sp, color = Color.Gray)
            }
        })
}

@Preview
@Composable
private fun AppOutlinedTextFieldPreview() {
    Surface() {
        AppOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "Email",
            keyboardType = KeyboardType.Email,
        )
    }
}
