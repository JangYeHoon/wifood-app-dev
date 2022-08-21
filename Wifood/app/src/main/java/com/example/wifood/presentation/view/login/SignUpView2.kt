package com.example.wifood.presentation.view.login

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.SMSReceiver
import com.example.wifood.presentation.util.checkPermission
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.util.Constants
import com.example.wifood.util.composableActivityViewModel
import com.google.android.gms.auth.api.phone.SmsRetriever
import java.security.Permission

@Composable
fun SignUpView2(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value

    val context = LocalContext.current

    var shouldRegisterReceiver by remember { mutableStateOf(false) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                shouldRegisterReceiver = true
            }
        }

//    DisposableEffect(context) {
//        Log.d(Constants.TAG, "SignUpView2: ${state}")
//        viewModel.onEvent(SignUpEvent.RequestCertNumber)
//
//        val br = SMSReceiver()
//
//        launcher.launch(Manifest.permission.RECEIVE_SMS)
//
//        if (shouldRegisterReceiver) {
//            context.registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
//        }
//
//        onDispose {
//            context.unregisterReceiver(br)
//        }
//    }

    LaunchedEffect(state.certNumber) {
        if (state.certNumber.length == 4) {
            viewModel.onEvent(SignUpEvent.Verify(state.certNumber))

            navController.navigate(Route.SignUp3.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            TitleText(
                text = SignUpData.phoneNumber,
                color = Color.Black
            )
            BasicTextField(
                value = state.certNumber,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.CertChanged(it))
                }
            )
        }
        if (state.isLoading) {
            ProgressIndicator()
        }
    }
}