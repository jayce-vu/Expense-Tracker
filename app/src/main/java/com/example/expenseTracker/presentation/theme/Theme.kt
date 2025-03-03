package com.example.expenseTracker.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = MintGreen,       // Use a brighter color for primary
    inversePrimary = LightGray,
    secondary = TealGreen,
    background = Color(0xFF121212), // Dark background
    surface = DarkTeal,       // Darker surface
    onPrimary = Color.Black,  // Text on primary color
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorPalette = lightColorScheme(
    primary = TealGreen,      // Softer primary color
    inversePrimary = DarkTeal,
    secondary = MintGreen,
    background = Color.White, // Light background
    surface = LightGray,      // Light gray surface
    onPrimary = Color.White,  // Text on primary color
    onBackground = Color.Black,
    onSurface = Color.Black
)
    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/

@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors =
        if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes(),
        content = content,
    )
}

/*Screen Background	MaterialTheme.colorScheme.background
Primary Button	MaterialTheme.colorScheme.primary
Text on Background	MaterialTheme.colorScheme.onBackground
Card/Dialog/Sheet	MaterialTheme.colorScheme.surface
Icons & Highlights	MaterialTheme.colorScheme.secondary
Text on Primary	MaterialTheme.colorScheme.inversePrimary
Error Messages	MaterialTheme.colorScheme.error*/
