package com.yogo.wifood.presentation.view.placeList.component

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogo.wifood.presentation.view.component.BottomSheetListItem
import com.yogo.wifood.presentation.view.mypage.MyPageViewModel
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteFormEvent
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@Composable
fun CameraAndAlbumBottomSheetContent(
    viewModel: PlaceInfoWriteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val takePhotoFromCameraLauncher = // 카메라로 사진 찍어서 가져오기
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    val file = File(viewModel.formState.currentPhotoPath)
                    viewModel.onEvent(PlaceInfoWriteFormEvent.PlaceImagesAdd(Uri.fromFile(file)))
                }
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                scope.launch {
                    takePhotoFromCameraLauncher.launch(
                        viewModel.getPictureIntent(context)
                    )
                }
            } else {
                Timber.i("false")
            }
        }

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
                            PlaceInfoWriteFormEvent.PlaceImagesAdd(uri)
                        )
                    }
                }
            }
        }

    Column {
        BottomSheetListItem(
            icon = Icons.Default.PhotoCamera,
            title = "카메라"
        ) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) -> {
                    scope.launch {
                        takePhotoFromCameraLauncher.launch(
                            viewModel.getPictureIntent(context)
                        )
                    }
                    Timber.i("Camera permission Accepted")
                }
                else -> {
                    Timber.i("Camera permission Denied")
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }
        BottomSheetListItem(
            icon = Icons.Default.PhotoAlbum,
            title = "앨범"
        ) {
            takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
        }
    }
}