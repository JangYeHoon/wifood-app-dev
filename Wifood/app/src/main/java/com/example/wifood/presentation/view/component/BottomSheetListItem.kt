package com.example.wifood.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.wifood.R

@Composable
fun BottomSheetListItem(icon: ImageVector, title: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(title) })
            .height(55.dp)
            .background(color = Color(0xFF262626))
            .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
//        Icon(painter = painterResource(id = icon), contentDescription = "Share", tint = Color.White)
        Icon(imageVector = icon, contentDescription = "Icon")
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, color = Color.White)
    }
}