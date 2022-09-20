package com.example.wifood.presentation.view.mypage.contents

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.BottomSheetListItem
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteFormEvent
import com.google.gson.Gson
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ProfileBottomSheet(
    navController: NavController,
    modalBottomSheetState: ModalBottomSheetState,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()



    Column {

        BottomSheetListItem(
            icon = Icons.Default.Edit,
            title = "앨범"
        ) {

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