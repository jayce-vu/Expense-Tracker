package com.example.expenseTracker.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
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
    isError: Boolean = false,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, style = TextStyle(color = Color.Gray, fontSize = 14.sp)) },
        modifier = modifier.fillMaxWidth().padding(top = if (isError && errorMessage != null) 0.dp else 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
        visualTransformation = visualTransformation,
        isError = isError,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        supportingText = if (isError && errorMessage != null) {
            { Text(text = errorMessage, color = Color.Red) }
        } else {
            null
        }
    )
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
