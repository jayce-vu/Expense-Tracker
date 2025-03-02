package com.example.expenseTracker.presentation.features.chooseLanguages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.features.chooseLanguages.viewModel.LanguageViewModel

@Composable
fun ChooseLanguages(viewModel: LanguageViewModel = hiltViewModel<LanguageViewModel>()) {
    val context = LocalContext.current
    val currentLanguage by viewModel.language.collectAsState()

    val languages = listOf("English" to "en", "Tiếng Việt" to "vi")

    var showDialog by remember { mutableStateOf(false) }

    TextButton(
        onClick = { showDialog = true },
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = if (currentLanguage == "vi") "\uD83C\uDDFB\uD83C\uDDF3" else "\uD83C\uDDEC\uD83C\uDDE7",
            fontSize = 24.sp,
            color = Color.White
        )
    }

    // Hiển thị Dialog khi `showDialog` = true
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(R.string.choose_language)) },
            text = {
                Column {
                    languages.forEach { (name, code) ->
                        TextButton(
                            onClick = {
                                viewModel.changeLanguage(code, context)
                                showDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = name)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
}
