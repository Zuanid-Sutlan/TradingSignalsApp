package com.devstudio.forexFusion.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val blueLight = Color(0xFF0066FF)
val blueDark = Color(0xFF003380)

val darkCardBackground = Color(0xFF313030)
val lightCardBackground = Color(0xFFECECEC)

val greenLight = Color(0xFF00D347)
val greenDark = Color(0xFF00802B)

val redLight = Color(0xFFFF0000)
val redDark = Color(0xFF801515)

val successColor = Color(0xFF44C544)
val errorColor = Color(0xFFCA4F4F)

// gradient colors
val gradientBlue = listOf(blueLight, blueDark)
val gradientGreen = listOf(greenLight, greenDark)
val gradientRed = listOf(redLight, redDark)


val green = Color(0xFF119911)
val redBright = Color(0xFFFF3B00)

//val (start, end) = calculateGradientOffsets(1000f, 1000f, 45f)

val endOffset = Offset(Float.POSITIVE_INFINITY, 0f)
val startOffset = Offset(0f, Float.POSITIVE_INFINITY)

val primaryGradient =
    Brush.linearGradient(gradientBlue, start = Offset.Zero, end = Offset.Infinite, TileMode.Mirror)
val greenGradient =
    Brush.linearGradient(gradientGreen, Offset.Zero, Offset.Infinite, TileMode.Mirror)
val redGradient = Brush.linearGradient(gradientRed, Offset.Zero, Offset.Infinite, TileMode.Mirror)





