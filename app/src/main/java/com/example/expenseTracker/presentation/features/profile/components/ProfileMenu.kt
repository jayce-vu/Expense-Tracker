package com.example.expenseTracker.presentation.features.profile.components
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.expenseTracker.R

@Composable
fun AppMenu() {
    Column {
        AppMenuItem(
            icon = Icons.Default.AccountCircle,
            title = "Account info",
            onClick = { /* Handle click */ }
        )
        AppMenuItem(
            icon = Icons.Default.Person,
            title = "Personal profile",
            onClick = { /* Handle click */ }
        )
        AppMenuItem(
            iconPainter = painterResource(R.drawable.ic_baseline_security_24),
            title = "Login and security",
            onClick = { /* Handle click */ }
        )
        AppMenuItem(
            icon = Icons.Default.Lock,
            title = "Data and privacy",
            onClick = { /* Handle click */ }
        )
        AppMenuItem(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            title = "Logout",
            isLogout = true,
            onClick = { /* Handle click */ }
        )
    }
}