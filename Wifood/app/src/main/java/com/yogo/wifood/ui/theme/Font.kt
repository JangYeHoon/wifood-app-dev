package com.yogo.wifood.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.yogo.wifood.R

val fontPretendard = FontFamily(
    Font(R.font.pbold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pmedium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pregular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.plight, FontWeight.Light, FontStyle.Normal),
    Font(R.font.pthin, FontWeight.Thin, FontStyle.Normal),
)

val fontTmoney = FontFamily(
    Font(R.font.tmoney_bold, FontWeight.Bold,FontStyle.Normal),
    Font(R.font.tmoney_normal, FontWeight.Normal,FontStyle.Normal)
)

val fontMiddleSchool = FontFamily(
    Font(R.font.middle_school_font, FontWeight.Normal, FontStyle.Normal),
)

val mainFont = fontPretendard