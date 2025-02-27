package com.example.expenseTracker.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.example.expenseTracker.utils.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable() (RowScope.() -> Unit) = {},
    title: String,
) {
    val color = MaterialTheme.colorScheme
    TopAppBar(
        modifier = Modifier.semantics { testTag = TestTags.CAT_SCREEN_APP_BAR },
        navigationIcon = navigationIcon,
        title = {
            Text(
                text = title,
                color = color.onBackground,
            )
        },
        actions = actions,
        colors =
        centerAlignedTopAppBarColors(
            containerColor = color.primary,
            titleContentColor = Color.White,
            navigationIconContentColor = color.onPrimary,
            actionIconContentColor = color.onSecondary,
        ),
    )
}
