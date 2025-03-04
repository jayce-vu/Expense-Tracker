package com.example.expenseTracker.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.example.expenseTracker.utils.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable() (RowScope.() -> Unit) = {},
    onNavigationIconClick: () -> Unit = {},
    showBackButton: Boolean = true,
    title: String,
) {
    val color = MaterialTheme.colorScheme
    TopAppBar(
        modifier = Modifier.semantics { testTag = TestTags.CAT_SCREEN_APP_BAR },
        navigationIcon = if (showBackButton) {
            navigationIcon ?: {
                IconButton(onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "navigation icon",
                    )
                }
            }
        } else {
            {}
        },
        title = {
            Text(
                text = title,
            )
        },
        actions = actions,
        colors =
        centerAlignedTopAppBarColors(
            containerColor = color.onPrimary.copy(alpha = 0f),
            titleContentColor = color.onSecondary,
            navigationIconContentColor = color.onSecondary,
            actionIconContentColor = color.onSecondary,
        ),
    )
}
