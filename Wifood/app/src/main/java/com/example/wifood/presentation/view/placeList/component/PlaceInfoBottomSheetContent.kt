package com.example.wifood.presentation.view.placeList.component

import android.net.Uri
import android.widget.Toast
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
import com.example.wifood.domain.model.Place
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.BottomSheetListItem
import com.example.wifood.presentation.view.placeList.PlaceInfoEvent
import com.example.wifood.presentation.view.placeList.PlaceInfoViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PlaceInfoBottomSheetContent(
    place: Place?,
    navController: NavController,
    modalBottomSheetState: ModalBottomSheetState,
    viewModel: PlaceInfoViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    Column {
        BottomSheetListItem(
            icon = Icons.Default.AddCircle,
            title = "맛집 수정"
        ) {
            viewModel.onEvent(PlaceInfoEvent.ViewChangeEvent)
            val placeJson = Uri.encode(Gson().toJson(place))
            navController.navigate("${Route.EditPlace.route}/${placeJson}")
        }
        BottomSheetListItem(
            icon = Icons.Default.Edit,
            title = "맛집 삭제"
        ) {
            viewModel.onEvent(PlaceInfoEvent.PlaceDeleteEvent)
            navController.popBackStack(
                "${Route.Main.route}?placeLat={placeLat}&placeLng={placeLng}",
                false
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        BottomSheetListItem(
            icon = Icons.Default.Delete,
            title = "취소"
        ) {
            scope.launch { modalBottomSheetState.hide() }
        }
    }
}