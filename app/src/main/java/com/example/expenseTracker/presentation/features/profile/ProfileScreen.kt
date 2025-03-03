package com.example.expenseTracker.presentation.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.components.CircleAvatar
import com.example.expenseTracker.presentation.features.authentication.viewModel.AuthenticationViewModel
import com.example.expenseTracker.presentation.features.profile.components.AppMenu
import com.example.expenseTracker.presentation.features.profile.viewModel.ProfileViewModel
import com.example.expenseTracker.presentation.layouts.BaseScreen

@Composable
fun ProfileScreen(navController: NavController) {
    val color = MaterialTheme.colorScheme
    val style = MaterialTheme.typography
    val viewModel = hiltViewModel<ProfileViewModel>()
    val authenticationViewModel = hiltViewModel<AuthenticationViewModel>()
    val state by viewModel.state.collectAsState()
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
                .padding(top = 200.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CircleAvatar(
                            size = 120.dp,
                            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/41/Sunflower_from_Silesia2.jpg"
                        )
                        Text(state.name, style = style.titleLarge)
                        Text(state.email, style = style.titleMedium)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AppMenu(onLogout = {
                        authenticationViewModel.logout()
                    })
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    Surface() {
        ProfileScreen(navController = NavController(LocalContext.current))
    }
}