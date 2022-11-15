package com.yogo.wifood.presentation.view.component

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
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
import androidx.navigation.NavController
import com.yogo.wifood.data.remote.dto.PlaceDto
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.main.MainEvent
import com.yogo.wifood.presentation.view.main.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun BottomSheetContent(
    group: Group?,
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    modalBottomSheetState: ModalBottomSheetState
) {
    val scope = rememberCoroutineScope()
    Column {
        BottomSheetListItem(
            icon = Icons.Default.AddCircle,
            title = "맛집 추가"
        ) {
            val placeJson = Uri.encode(Gson().toJson(PlaceDto(groupId = group!!.groupId).toPlace()))
            navController.navigate("${Route.PlaceInputNameAndVisited.route}/${placeJson}")
        }
        BottomSheetListItem(
            icon = Icons.Default.Edit,
            title = "맛집 그룹 수정"
        ) {
            val groupJson = Uri.encode(Gson().toJson(group))
            navController.navigate("${Route.GroupEdit.route}/${groupJson}")
        }
        BottomSheetListItem(
            icon = Icons.Default.Delete,
            title = "맛집 그룹 삭제"
        ) {
            viewModel.onEvent(MainEvent.DeleteGroupEvent(group!!.groupId))
            scope.launch { modalBottomSheetState.hide() }
        }
        Spacer(modifier = Modifier.height(8.dp))
        BottomSheetListItem(
            icon = Icons.Default.Delete,
            title = "취소"
        ) {
            scope.launch { modalBottomSheetState.hide() }
        }
    }
}