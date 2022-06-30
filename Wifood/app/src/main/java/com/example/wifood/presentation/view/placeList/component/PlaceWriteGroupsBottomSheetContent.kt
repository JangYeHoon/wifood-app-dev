package com.example.wifood.presentation.view.placeList.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wifood.presentation.view.component.BottomSheetListItem
import com.example.wifood.presentation.view.placeList.PlaceInfoEvent
import com.example.wifood.presentation.view.placeList.PlaceInfoWriteFormEvent
import com.example.wifood.presentation.view.placeList.PlaceInfoWriteViewModel
import kotlinx.coroutines.launch

@Composable
fun PlaceWriteGroupsBottomSheetContent(
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val formState = viewModel.formState
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Column {
        LazyColumn {
            items(formState.groups) { group ->
                BottomSheetListItem(
                    icon = Icons.Default.AddCircle,
                    title = group.name,
                    onItemClick = {
                        scope.launch {
                            viewModel.onEvent(PlaceInfoWriteFormEvent.GroupSelected(group))
                        }
                    }
                )
            }
        }
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