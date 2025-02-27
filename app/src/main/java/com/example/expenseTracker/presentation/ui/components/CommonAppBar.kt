package com.example.expenseTracker.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.ui.features.chooseLanguages.ChooseLanguages
import com.example.expenseTracker.utils.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    showRefreshButton: Boolean,
    onNavigationIconClick: () -> Unit,
    onRefreshCall: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.semantics { testTag = TestTags.CAT_SCREEN_APP_BAR },

        navigationIcon = {
            IconButton(
                onClick = { onNavigationIconClick() },
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    modifier = Modifier,
                    contentDescription = TestTags.ACTION_ICON,
                )
            }
        },
        expandedHeight = TopAppBarDefaults.MediumAppBarCollapsedHeight,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = colorResource(id = R.color.white),
            )
        },
        actions = {
            // Show the "Refresh" button only when showRefreshButton is true
            Row {
                if (showRefreshButton) {
                    IconButton(
                        onClick = onRefreshCall,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = TestTags.REFRESH_ACTION,
                        )
                    }
                }
                ChooseLanguages()
            }
        },
        colors =
        centerAlignedTopAppBarColors(
            containerColor = colorResource(R.color.colorPrimary),
            titleContentColor = Color.White,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary,
        ),
    )
}
