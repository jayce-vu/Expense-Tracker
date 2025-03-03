package com.example.expenseTracker.presentation.navigation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomBottomNavigation(
    modifier: Modifier = Modifier,
    barHeight: Dp = 70.dp,
    fabSize: Dp = 64.dp,
    fabIconSize: Dp = 32.dp,
    cardTopCornerSize: Dp = 24.dp,
    cardElevation: Dp = 8.dp,
    fabIcon: ImageVector,
    buttons: List<CustomBottomNavItemData>,
    selectedItemColor: Color = Color(0xFF438883),
    unselectedItemColor: Color = Color(0xFF6F767E).copy(alpha = 0.7f),
    backgroundColor: Color = Color.White,
    fabBackgroundColor: Color = Color(0xFF438883),
    fabBorderColor: Color = Color.White, // New customizable FAB border color
    fabBorderSize: Dp = 2.dp, // Customizable FAB border size
    itemPadding: Dp = 8.dp, // Customizable item padding
    spacingBetweenIconAndText: Dp = 4.dp, // Customizable spacing between icon and text
    iconSize: Dp = 32.dp, // Customizable icon size
    labelFontSize: TextUnit = 12.sp, // Customizable label font size
    labelFontWeight: FontWeight = FontWeight.Normal, // Customizable font weight
    onFabClick: () -> Unit = {},
    hideLabel: Boolean = false,
) {
    require(buttons.size == 2) { "BottomBar must have exactly 2 buttons" }
    val paddingValues = WindowInsets.navigationBars.asPaddingValues()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = paddingValues.calculateBottomPadding())
            .height(barHeight + fabSize / 2)
    ) {
        // Background card for the bottom bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
            shape = RoundedCornerShape(
                topStart = cardTopCornerSize,
                topEnd = cardTopCornerSize
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CustomBottomNavItem(
                    buttons[0],
                    selectedItemColor,
                    unselectedItemColor,
                    hideLabel,
                    itemPadding,
                    spacingBetweenIconAndText,
                    iconSize,
                    labelFontSize,
                    labelFontWeight
                )
                Spacer(modifier = Modifier.size(fabSize)) // Spacer for the FAB
                CustomBottomNavItem(
                    buttons[1],
                    selectedItemColor,
                    unselectedItemColor,
                    hideLabel,
                    itemPadding,
                    spacingBetweenIconAndText,
                    iconSize,
                    labelFontSize,
                    labelFontWeight
                )
            }
        }

        // Floating Action Button in the center
        LargeFloatingActionButton(
            modifier = Modifier
                .size(fabSize)
                .align(Alignment.Center)
                .offset(x = 0.dp, y = (-12).dp)
                .border(fabBorderSize, fabBorderColor, CircleShape), // Customizable FAB border
            onClick = { onFabClick() },
            shape = CircleShape,
            containerColor = fabBackgroundColor,
        ) {
            Icon(
                imageVector = fabIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(fabIconSize)
            )
        }
    }
}

@Composable
fun CustomBottomNavItem(
    itemData: CustomBottomNavItemData,
    selectedItemColor: Color,
    unselectedItemColor: Color,
    hideLabel: Boolean,
    itemPadding: Dp = 8.dp, // Customizable padding for the item
    spacingBetweenIconAndText: Dp = 4.dp, // Customizable spacing between the icon and the text
    iconSize: Dp = 24.dp, // Customizable icon size
    labelFontSize: TextUnit = 12.sp, // Customizable label font size
    labelFontWeight: FontWeight = FontWeight.Normal, // Customizable label font weight
) {
    val drawableResId =
        if (itemData.selected) itemData.drawableSelectedResId else itemData.drawableUnselectedResId
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = itemPadding)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                itemData.onClick()
            }
    ) {
        if (itemData.imageVector != null) {
            Icon(
                imageVector = itemData.imageVector,
                contentDescription = null,
                tint = if (itemData.selected) selectedItemColor else unselectedItemColor,
                modifier = Modifier.size(iconSize)
            )
        } else if (drawableResId != null) {
            Icon(
                painter = painterResource(id = drawableResId),
                contentDescription = null,
                tint = if (itemData.selected) selectedItemColor else unselectedItemColor,
                modifier = Modifier.size(iconSize)
            )
        }
        Spacer(modifier = Modifier.height(spacingBetweenIconAndText))
        if (!hideLabel)
            Text(
                text = itemData.label,
                color = if (itemData.selected) selectedItemColor else unselectedItemColor,
                fontSize = labelFontSize, // Customizable label font size
                fontWeight = labelFontWeight, // Customizable label font weight
                style = MaterialTheme.typography.labelSmall,
            )
    }
}

data class CustomBottomNavItemData(
    val imageVector: ImageVector? = null, // For Material Icons
    val drawableSelectedResId: Int? = null, // For Drawable Resources
    val drawableUnselectedResId: Int? = null, // For Drawable Resources
    val selected: Boolean = false,
    val onClick: () -> Unit,
    val label: String
)

@Preview
@Composable
private fun PreviewBottomNavigation() {
    Surface() {
        CustomBottomNavigation(
            fabIcon = Icons.Filled.Add,
            buttons = listOf(
                CustomBottomNavItemData(
                    label = "Home",
                    selected = true,
                    imageVector = Icons.Filled.Home,
                    onClick = {}
                ),
                CustomBottomNavItemData(
                    label = "Profile",
                    selected = false,
                    imageVector = Icons.Filled.Person,
                    onClick = {}
                ),
            )
        )
    }
}
