package com.example.wifood.view.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.wifood.R

@Composable
fun BottomSheetContent() {
    val context = LocalContext.current
    Column {
        BottomSheetListItem(
            icon = Icons.Default.AddCircle,
            title = "맛집 추가"
        ) { title ->
            Toast.makeText(
                context,
                title,
                Toast.LENGTH_SHORT
            ).show()
        }
        BottomSheetListItem(
            icon = Icons.Default.Edit,
            title = "맛집 그룹 수정"
        ) { title ->
            Toast.makeText(
                context,
                title,
                Toast.LENGTH_SHORT
            ).show()
        }
        BottomSheetListItem(
            icon = Icons.Default.Delete,
            title = "맛집 그룹 삭제"
        ) { title ->
            Toast.makeText(
                context,
                title,
                Toast.LENGTH_SHORT
            ).show()
        }
        Spacer(modifier = Modifier.height(8.dp))
        BottomSheetListItem(
            icon = Icons.Default.Delete,
            title = "취소"
        ) { title ->
            Toast.makeText(
                context,
                title,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}