package br.com.coupledev.todocompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val PurpleLight = Color(0xFF9B8DCE)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)
val Gray = Color(0xFF1E222B)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = Color(0xFFFFFFFF)

val ColorScheme.splashBackground: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple40
val ColorScheme.contentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else Color.White

val ColorScheme.secondaryContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else LightGray

val ColorScheme.backgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple40

val ColorScheme.borderButtonOverlay: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MediumGray else Purple40

val ColorScheme.overlayBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Gray else MaterialTheme.colorScheme.surface

val ColorScheme.secondaryBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Gray else PurpleLight

val ColorScheme.taskItemBackground: Color
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGray else Color.White

val ColorScheme.taskItemTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else DarkGray