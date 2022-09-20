package com.example.wifood.presentation.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.presentation.view.mypage.contents.ModifyUserProfileContent
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
            }
        }
    }

    ModifyUserProfileContent(
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onCameraButtonClicked = {
            scope.launch {
                takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
            }
        },
        onNicknameTextChanged = {

        },
        onCompleteButtonClicked = {
            viewModel.onEvent(MyPageEvent.ModifyProfile)
        }
    )


}