package com.example.expenseTracker.presentation.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.components.AppButton
import com.example.expenseTracker.presentation.components.AppOutlinedTextField
import com.example.expenseTracker.presentation.components.GoogleSignInButton
import com.example.expenseTracker.presentation.features.login.viewModel.LoginErrorState
import com.example.expenseTracker.presentation.features.login.viewModel.LoginLoadingState
import com.example.expenseTracker.presentation.features.login.viewModel.LoginSuccessState
import com.example.expenseTracker.presentation.features.login.viewModel.LoginViewModel
import com.example.expenseTracker.presentation.layouts.BaseScreen
import com.example.expenseTracker.presentation.navigation.NavigationScreens
import com.example.expenseTracker.presentation.theme.White
import com.example.expenseTracker.utils.extensions.navigateAndReplace

@Composable
fun LoginScreen(navController: NavController) {
    val color = MaterialTheme.colorScheme
    val style = MaterialTheme.typography
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val viewModel = hiltViewModel<LoginViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        if (state is LoginSuccessState) {
            navController.navigateAndReplace(NavigationScreens.Home.screenRoute)
        }
    }
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Login Header
                Text(
                    text = "LOGIN",
                    fontSize = 32.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            if (state is LoginErrorState) {
                                Text(
                                    (state as LoginErrorState).error ?: "",
                                    style = style.titleMedium.copy(color = Color.Red)
                                )
                            }
                            // Email Field
                            AppOutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = "Email",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                            )

                            // Password Field
                            AppOutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = "Password",
                                keyboardType = KeyboardType.Password,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                visualTransformation = PasswordVisualTransformation()
                            )

                            Spacer(Modifier.height(24.dp))

                            // Login Button
                            AppButton(
                                onClick = {
                                    if (state !is LoginLoadingState) {
                                        viewModel.login(email, password)
                                    }
                                },
                                text = "Login"
                            )

                        }
                        // Sign in with Google Button
                        GoogleSignInButton() {
                            /* handle google sign in */
                        }

                        HorizontalDivider()

                        // Sign up link
                        Text(
                            text = "No account? Sign up",
                            color = Color(0xFF2E7D32), // Orange color for the link
                            modifier = Modifier.clickable {
                                navController.navigateAndReplace(NavigationScreens.SignUpScreen.screenRoute)
                            }
                        )
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
        LoginScreen(navController = NavController(LocalContext.current))
    }
}