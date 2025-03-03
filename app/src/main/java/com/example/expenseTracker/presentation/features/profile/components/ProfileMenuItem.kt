package com.example.expenseTracker.presentation.features.profile.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppMenuItem(
    icon: ImageVector? = null,
    title: String,
    isLogout: Boolean = false,
    iconPainter: Painter? = null,
    onClick: (() -> Unit)? = null
) {
    val iconSize = 28.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick?.invoke() })
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(iconPainter == null && icon != null){
            Icon(
                imageVector = icon,
                contentDescription = "$title Icon",
                tint = if (isLogout) Color.Red else Color.Gray,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(iconSize),
            )
        } else if (icon == null && iconPainter != null) {
            Icon(
                painter = iconPainter,
                contentDescription = "$title Icon",
                tint = if (isLogout) Color.Red else Color.Gray,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(iconSize),
            )
        }
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                color = if (isLogout) Color.Red else Color.Black,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.weight(1f)
        )
    }
}