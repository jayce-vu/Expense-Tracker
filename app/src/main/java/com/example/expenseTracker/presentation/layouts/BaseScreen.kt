package com.example.expenseTracker.presentation.layouts

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.components.AppBarBackground
import com.example.expenseTracker.presentation.components.CommonAppBar

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    showBackground: Boolean = true,
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
    ) { paddingValues ->
        var baseScreenModifier = modifier
        if (!disablePadding) {
            baseScreenModifier = baseScreenModifier.padding(paddingValues)
        }
        Box {
            if (showBackground) {
                AppBarBackground()
            }
            if (showAppBar) {
                CommonAppBar(
                    showBackButton = showBackButton,
                    title = title,
                    onNavigationIconClick = {
                        navController.popBackStack()
                        onBackPress.invoke()
                    },
                    navigationIcon = navigationIcon,
                    actions = actions,
                    contentColor = if(!showBackground) Color.Black else null
                )
            }
        }
        Box(modifier = baseScreenModifier.padding(top = if(showAppBar) 25.dp else 0.dp), contentAlignment = Alignment.TopCenter) {
            content(paddingValues)
        }
    }
}