package com.example.expenseTracker.presentation.ui.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.ui.layouts.BaseScreen
import com.example.expenseTracker.presentation.ui.navigation.NavigationScreens
import com.example.expenseTracker.utils.extensions.navigateAndReplace

@Composable
fun LoginScreen(navController: NavController) {
    val color = MaterialTheme.colorScheme
    val style = MaterialTheme.typography
    BaseScreen(
        showAppBar = false,
        disablePadding = true,
        navController = navController
    ) {
        Image(
            painter = painterResource(R.drawable.app_bar_background),
            "Image background"
        )
        Box(
            modifier = Modifier
                .padding(top = 150.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Button(onClick = {
                    navController.navigateAndReplace(NavigationScreens.Home.screenRoute)
                }) {
                    Text("LOGIN")
                }
                Button(onClick = {
                    navController.navigateAndReplace(NavigationScreens.SignUpScreen.screenRoute)
                }) {
                    Text("Sign up")
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    Surface() {
        LoginScreen(navController = NavController(LocalContext.current))
    }
}