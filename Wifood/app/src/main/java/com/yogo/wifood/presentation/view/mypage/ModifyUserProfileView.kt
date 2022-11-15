package com.yogo.wifood.presentation.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yogo.wifood.presentation.view.component.ProgressIndicator
import com.yogo.wifood.presentation.view.login.util.ValidationEvent
import com.yogo.wifood.presentation.view.main.util.MainData
import com.yogo.wifood.presentation.view.mypage.MyPageEvent
import com.yogo.wifood.presentation.view.mypage.MyPageViewModel
import com.yogo.wifood.presentation.view.mypage.contents.ModifyUserProfileContent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyUserProfileView(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
    val takePhotoFromAlbumLauncher = // 갤러리에서 사진 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    scope.launch {
                        MainData.image = uri.toString()
                        viewModel.onEvent(
                            MyPageEvent.ImageAdd(uri)
                        )
                    }
                }
            }
        }

    LaunchedEffect(true) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.popBackStack()
                }
                is ValidationEvent.Loading -> {
                    isLoading = true
                }
            }
        }
    }

    if (isLoading) {
        ProgressIndicator()
    }

    ModifyUserProfileContent(
        usernickNameText = viewModel.state.value.nickname,
        onUserNicknameChanged = {
            viewModel.onEvent(MyPageEvent.NicknameChanged(it))
        },
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onCameraButtonClicked = {
            scope.launch {
                takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
            }
        },
        onCompleteButtonClicked = {
            viewModel.onEvent(MyPageEvent.ModifyProfile)
        }
    )
}