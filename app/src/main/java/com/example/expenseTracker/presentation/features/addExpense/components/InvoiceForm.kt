package com.example.expenseTracker.presentation.features.addExpense.components

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expenseTracker.data.services.postModel.PostExpense
import com.example.expenseTracker.data.services.responseModels.CategoryResponse
import com.example.expenseTracker.presentation.components.AppOutlinedTextField
import com.example.expenseTracker.presentation.components.LabeledCheckbox
import com.example.expenseTracker.presentation.components.buttons.AppButton
import com.example.expenseTracker.presentation.components.buttons.AppDateSelectorButton
import com.example.expenseTracker.presentation.components.buttons.AppDropdownButton
import com.example.expenseTracker.presentation.components.buttons.AttachFileButton
import com.example.expenseTracker.presentation.features.addExpense.viewModel.AddExpenseInitialState
import com.example.expenseTracker.presentation.features.addExpense.viewModel.AddExpenseLoadingState
import com.example.expenseTracker.presentation.features.addExpense.viewModel.AddExpenseState
import com.example.expenseTracker.utils.DateTimeFormatUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar


@Composable
fun InvoiceForm(
    state: AddExpenseState,
    categoriesState: List<CategoryResponse>,
    onAddExpense: (postExpense: PostExpense) -> Unit
) {
    val context = LocalContext.current
    var category by remember { mutableStateOf<CategoryResponse?>(null) }
    var amount by remember { mutableStateOf("") }
    var checkedInCome by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(Calendar.getInstance().time) }
    var invoices by remember { mutableStateOf<List<File>>(arrayListOf()) }

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
            AppDropdownButton(options = categoriesState, selectedOption = null, itemTitle = {
                it?.name
            }, onOptionSelected = {
                category = it
            })
            // Amount TextField
            AppOutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                keyboardType = KeyboardType.Decimal,
                label = "Amount"
            )
            LabeledCheckbox("Income", isChecked = checkedInCome) {
                checkedInCome = it
            }

            AppOutlinedTextField(
                value = description,
                onValueChange = { description = it },
                keyboardType = KeyboardType.Text,
                label = "Description"
            )

            // Date TextField with Calendar Icon
            AppDateSelectorButton(context = context, selectedDate = date, onDateSelected = {
                date = it
            })

            // Invoice Section (Placeholder for adding invoice)
            AttachFileButton(
                title = "Add invoice",
                onFileSelected = { uri ->
                    Log.d("FilePicker", "Selected files: $uri")
                    invoices =
                        uri.filterNotNull().mapNotNull { item -> getFileFromUri(context, item) }
                },
            )
            // Create Button
            AppButton(
                text = "Create",
                onClick = {
                    if (state != AddExpenseLoadingState) {
                        val postExpense = PostExpense(
                            categoryId = category?.id ?: "",
                            amount = amount.toDouble(),
                            description = description,
                            date = DateTimeFormatUtils.convertAndFormatDate(date),
                            invoices = invoices,
                            isIncome = checkedInCome,
                            categoryName = category?.name ?: ""
                        )
                        onAddExpense.invoke(postExpense)
                    }
                },
            )
        }
    }
}

private fun getFileFromUri(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    val fileName = getFileName(context, uri) ?: return null
    val file = File(context.cacheDir, fileName)

    try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    return file
}

private fun getFileName(context: Context, uri: Uri): String? {
    var name: String? = null
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        }
    }
    return name
}

@Preview(showBackground = true)
@Composable
fun PreviewInvoiceForm() {
    InvoiceForm(AddExpenseInitialState, emptyList()) {
        // Handle the onAddExpense callback
    }
}