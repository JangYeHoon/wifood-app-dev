package com.example.wifood.presentation.view.placeList.component

import android.net.Uri
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.domain.model.Place
import com.example.wifood.presentation.view.component.BottomSheetListItem
import com.example.wifood.presentation.view.placeList.PlaceInfoEvent
import com.example.wifood.presentation.view.placeList.PlaceInfoViewModel

@Composable
fun PlaceInfoBottomSheetContent(
    navController: NavController,
    viewModel: PlaceInfoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Column {
        BottomSheetListItem(
            icon = Icons.Default.AddCircle,
            title = "맛집 수정"
        ) { title ->
            Toast.makeText(
                context,
                title,
                Toast.LENGTH_SHORT
            ).show()
        }
        BottomSheetListItem(
            icon = Icons.Default.Edit,
            title = "맛집 삭제"
        ) { title ->
            Toast.makeText(
                context,
                title,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.onEvent(PlaceInfoEvent.PlaceDeleteEvent)
            navController.popBackStack()
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