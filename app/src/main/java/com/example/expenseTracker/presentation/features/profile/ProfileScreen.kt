package com.example.expenseTracker.presentation.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.components.CircleAvatar
import com.example.expenseTracker.presentation.features.chooseLanguages.ChooseLanguages
import com.example.expenseTracker.presentation.layouts.BaseScreen

@Composable
fun ProfileScreen(navController: NavController) {
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
            Column(modifier = Modifier.fillMaxSize()) {
                CircleAvatar(imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/41/Sunflower_from_Silesia2.jpg")
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)) {
                    ChooseLanguages()
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