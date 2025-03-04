package com.example.expenseTracker.presentation.features.signup

import android.widget.Toast
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expenseTracker.presentation.components.AppBarBackground
import com.example.expenseTracker.presentation.components.buttons.AppButton
import com.example.expenseTracker.presentation.components.AppOutlinedTextField
import com.example.expenseTracker.presentation.features.signup.viewModel.SignUpErrorState
import com.example.expenseTracker.presentation.features.signup.viewModel.SignUpLoadingState
import com.example.expenseTracker.presentation.features.signup.viewModel.SignUpSuccessState
import com.example.expenseTracker.presentation.features.signup.viewModel.SignUpViewModel
import com.example.expenseTracker.presentation.layouts.BaseScreen
import com.example.expenseTracker.presentation.navigation.NavigationScreens
import com.example.expenseTracker.presentation.theme.White
import com.example.expenseTracker.utils.RegEx
import com.example.expenseTracker.utils.extensions.navigateAndReplace

@Composable
fun SignUpScreen(navController: NavController) {
    val color = MaterialTheme.colorScheme
    val style = MaterialTheme.typography
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var verifyPassword by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    val viewModel = hiltViewModel<SignUpViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is SignUpSuccessState) {
            Toast.makeText(context, "Your information has been successfully registered!", Toast.LENGTH_LONG).show()
            navController.navigateAndReplace(NavigationScreens.Login.screenRoute)
        }
    }
    BaseScreen(
        showAppBar = false,
        disablePadding = true,
        navController = navController
    ) {
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
                    text = "SIGN UP",
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
                            if (state is SignUpErrorState) {
                                Text(
                                    (state as SignUpErrorState).error ?: "",
                                    style = style.titleMedium.copy(color = Color.Red)
                                )
                            }
                            AppOutlinedTextField(
                                value = email,
                                regex = Regex(RegEx.EMAIL),
                                errorMessage = "Email is not valid",
                                onValueChange = { email = it },
                                label = "Email",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                            )

                            AppOutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                label = "Full name",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                            )

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

                            AppOutlinedTextField(
                                value = verifyPassword,
                                onValueChange = { verifyPassword = it },
                                label = "Verify password",
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
                                    if (state !is SignUpLoadingState) {
                                        viewModel.signup(email, fullName, password, verifyPassword)
                                    }
                                },
                                text = "Sign up"
                            )

                        }

                        HorizontalDivider()

                        // Sign up link
                        Text(
                            text = "already have an account? Login",
                            color = Color(0xFF2E7D32), // Orange color for the link
                            modifier = Modifier.clickable {
                                navController.navigateAndReplace(NavigationScreens.Login.screenRoute)
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
        SignUpScreen(navController = NavController(LocalContext.current))
    }
}