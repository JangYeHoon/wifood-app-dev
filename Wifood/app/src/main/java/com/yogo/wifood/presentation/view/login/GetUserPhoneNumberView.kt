package com.yogo.wifood.presentation.view.login.new_compose_views

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.login.SignUpEvent
import com.yogo.wifood.presentation.view.login.SignUpViewModel
import com.yogo.wifood.presentation.view.login.contents.GetUserPhoneNumberContent
import com.yogo.wifood.presentation.view.login.util.SignUpData
import com.yogo.wifood.presentation.view.login.util.ValidationEvent
import com.yogo.wifood.presentation.view.login.util.ViewItem
import com.yogo.wifood.util.composableActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GetUserPhoneNumberView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    var done by remember { mutableStateOf(false) }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                navController.navigate(Route.GetAuthNumber.route)
            } else {
                // TODO
                Timber.i("false")
            }
        }

    LaunchedEffect(true) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    done = true
                }
                is ValidationEvent.Error -> {
                    done = false
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    LaunchedEffect(state.phoneNumber) {
        viewModel.checkForm(ViewItem.SignUpView1)
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        GetUserPhoneNumberContent(
            isLoading = state.isLoading,
            phoneNumber = state.phoneNumber,
            onPhoneNumberValueChanged = {
                viewModel.onEvent(SignUpEvent.PhoneNumChanged(it))
            },
            onButtonClicked = {
//                when (PackageManager.PERMISSION_GRANTED) {
//                    ContextCompat.checkSelfPermission(
//                        context,
//                        Manifest.permission.SEND_SMS
//                    ) -> {
//                        navController.navigate(Route.GetAuthNumber.route)
//                    }
//                    else -> {
//                        permissionLauncher.launch(Manifest.permission.SEND_SMS)
//                    }
//                }
                if (SignUpData.exist) {
                    com.yogo.wifood.WifoodApp.pref.setString("Initial_Flag", "1")
                    navController.navigate(Route.Main.route)
                } else {
                    navController.navigate(Route.Agreement.route)
                }
            },
            isButtonOn = done
            // kangjik : 잠깐 안되서 일단 억지로 오픈해둠
        )
    }
}
