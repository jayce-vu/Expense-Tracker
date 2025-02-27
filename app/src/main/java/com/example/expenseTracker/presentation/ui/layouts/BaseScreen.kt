package com.example.expenseTracker.presentation.ui.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.ui.components.CommonAppBar

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    showBackButton: Boolean = true,
    onBackPress: () -> Unit = {},
    disablePadding: Boolean = false,
    showAppBar: Boolean = true,
    navController: NavController,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable() (RowScope.() -> Unit) = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val color = MaterialTheme.colorScheme
    Scaffold(
        containerColor = color.background,
        topBar = {
            if (showAppBar)
                CommonAppBar(
                    title = title,
                    navigationIcon = navigationIcon ?: {
                        if (showBackButton) {
                            IconButton({
                                navController.popBackStack()
                                onBackPress.invoke()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "asd",
                                    tint = color.onBackground
                                )
                            }
                        }
                    },
                    actions = actions
                )
        }
    ) { paddingValues ->
        val baseScreenModifier = Modifier
        if (!disablePadding) {
            modifier.padding(paddingValues)
        }
        Box(modifier = baseScreenModifier, contentAlignment = Alignment.TopCenter) {
            content(paddingValues)
        }
    }
}