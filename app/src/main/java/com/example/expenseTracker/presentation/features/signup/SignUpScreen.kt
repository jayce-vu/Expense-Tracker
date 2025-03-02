package com.example.expenseTracker.presentation.features.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.layouts.BaseScreen
import com.example.expenseTracker.presentation.navigation.NavigationScreens
import com.example.expenseTracker.utils.extensions.navigateAndReplace

@Composable
fun SignUpScreen(navController: NavController) {
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
                }) {
                    Text("Sign up")
                }
                Row {
                    Text("You already have an account?")
                    TextButton(onClick = {
                        navController.navigateAndReplace(NavigationScreens.LoginScreen.screenRoute)
                    }) {
                        Text("Login")
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    Surface() {
        SignUpScreen(navController = NavController(LocalContext.current))
    }
}