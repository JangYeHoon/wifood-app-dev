package com.example.wifood.presentation.view.component

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
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.MainViewModel
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber

@Composable
fun BottomSheetContent(
    group: Group?,
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
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
            val placeJson = Uri.encode(Gson().toJson(PlaceDto(groupId = group!!.groupId).toPlace()))
            navController.navigate("${Route.EditPlace.route}/${placeJson}")
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
            val groupJson = Uri.encode(Gson().toJson(group))
            navController.navigate("${Route.GroupEdit.route}/${groupJson}")
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
            viewModel.onEvent(MainEvent.DeleteGroupEvent(group!!.groupId))
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