package com.example.expenseTracker.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.expenseTracker.R

@Composable
fun AppBarBackground(){
    return Image(
        painter = painterResource(R.drawable.app_bar_background),
        "Image background"
    )
}