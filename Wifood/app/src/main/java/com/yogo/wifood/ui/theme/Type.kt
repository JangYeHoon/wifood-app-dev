package com.yogo.wifood.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R

val robotoFamily = FontFamily(
    //Font(R.font.roboto_black, FontWeight.Black),
    //Font(R.font.roboto_bold, FontWeight.Bold),
    //Font(R.font.roboto_light, FontWeight.Light),
    //Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal),
    //Font(R.font.roboto_thin, FontWeight.Thin)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    ),
    h1 = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Black,
        fontSize = 25.sp
    )
)