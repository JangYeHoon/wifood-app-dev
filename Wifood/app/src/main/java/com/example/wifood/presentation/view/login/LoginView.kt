package com.example.wifood.presentation.view.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.WifoodApp
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.ProgressIndicator
import com.example.wifood.presentation.view.login.component.*
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.ui.theme.fontPretendard
import com.example.wifood.util.Constants
import com.example.wifood.util.Constants.INVALID
import com.example.wifood.util.Constants.NAVER_CLIENT_NAME
import com.example.wifood.util.Constants.NAVER_CLIENT_SECRET
import com.example.wifood.util.Constants.VALID
import com.example.wifood.util.getActivity
import com.example.wifood.view.ui.theme.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener

@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val formState = viewModel.formState
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState() // for horizontal mode screen
    val autoLogin = remember {
        WifoodApp.pref.getString("auto_login", INVALID)
    }
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    val email = handleSignInResult(task)
                    if (!email.isNullOrBlank()) {
                        navController.navigate("${Route.Joinin.route}?email=$email")
                    }
                }
            }
        }

    LaunchedEffect(key1 = autoLogin) {
        when (autoLogin) {
            VALID -> {
                navController.navigate(Route.Main.route)
            }
        }
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    WifoodApp.pref.setString("user_id", viewModel.formState.email)
                    WifoodApp.pref.setString("auto_login", VALID)
                    formState.clear()
                    navController.navigate(Route.Main.route)
                }
                is ValidationEvent.Error -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {

        LoginErrorText(
            formState.emailError ?: formState.passwordError ?: "Invalid",
            !(formState.emailError.isNullOrBlank() && formState.passwordError.isNullOrBlank())
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePaddingValue.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            LogoImage(
                width = 86,
                height = 28
            )
            Spacer(Modifier.height(50.dp))
            RoundedTextField(
                text = formState.email,
                placeholder = "아이디",
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(LoginFormEvent.EmailChanged(it))
                    }
                },
                isError = !formState.emailError.isNullOrBlank()
            )
            Spacer(Modifier.height(5.dp))
            RoundedTextField(
                text = formState.password,
                placeholder = "비밀번호",
                isPassword = true,
                onValueChange = {
                    scope.launch {
                        viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                    }
                },
                imeAction = ImeAction.Done,
                isError = !formState.passwordError.isNullOrBlank()
            )
            Spacer(Modifier.height(10.dp))
            MainButton(
                text = "로그인",
                onClick = {
                    scope.launch {
                        viewModel.onEvent(LoginFormEvent.Login)
                    }
                }
            )
            Spacer(Modifier.height(5.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                TransparentButton(
                    text = "아이디/비밀번호찾기",
                    textColor = Gray03Color,
                    onClick = {
                        formState.clear()
                        navController.navigate(Route.MobileAuthentication.route)
                    }
                )
                Spacer(Modifier.width(10.dp))
                TransparentButton(
                    text = "회원가입",
                    textColor = Gray01Color,
                    onClick = {
                        formState.clear()
                        navController.navigate(Route.MobileAuthentication.route)
                    }
                )
            }
            Spacer(Modifier.height(49.dp))
            Divider(
                color = DividerColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = "SNS 계정으로 간편 로그인/회원가입",
                color = Gray01Color,
                fontSize = 12.sp,
                fontFamily = fontPretendard,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(18.dp))
            // Debuging from here
            Row() {
                var name: String
                var email: String
                var gender: String
                var phone: String
                SnsIconButton(
                    resourceId = R.drawable.ic_naver_login_icon,
                    onClick = {
                        val oAuthLoginCallback = object : OAuthLoginCallback {
                            override fun onError(errorCode: Int, message: String) {
                                val naverAccessToken = NaverIdLoginSDK.getAccessToken()
                                Log.e("NAVER", "naverAccessToken : $naverAccessToken")
                            }

                            override fun onFailure(httpStatus: Int, message: String) {
                                TODO("Not yet implemented")
                            }

                            override fun onSuccess() {
                                NidOAuthLogin().callProfileApi(object :
                                    NidProfileCallback<NidProfileResponse> {
                                    override fun onSuccess(result: NidProfileResponse) {
                                        email = result.profile?.email.toString()
                                        gender = result.profile?.gender.toString()
                                        phone = result.profile?.mobile.toString()
                                        navController.navigate(
                                            "${Route.Joinin.route}?email=$email&gender=$gender&phone=$phone"
                                        )
                                        NidOAuthLogin().callDeleteTokenApi(
                                            context,
                                            object : OAuthLoginCallback {
                                                override fun onSuccess() {
                                                    //서버에서 토큰 삭제에 성공한 상태입니다.
                                                }

                                                override fun onFailure(
                                                    httpStatus: Int,
                                                    message: String
                                                ) {
                                                    // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                                                    // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                                                }

                                                override fun onError(
                                                    errorCode: Int,
                                                    message: String
                                                ) {
                                                    // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                                                    // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                                                }
                                            })
                                    }

                                    override fun onError(errorCode: Int, message: String) {
                                        TODO("Not yet implemented")
                                    }

                                    override fun onFailure(httpStatus: Int, message: String) {
                                        TODO("Not yet implemented")
                                    }
                                }
                                )
                            }
                        }
                        NaverIdLoginSDK.initialize(
                            context,
                            Constants.NAVER_CLIENT_ID,
                            NAVER_CLIENT_SECRET,
                            NAVER_CLIENT_NAME
                        )
                        NaverIdLoginSDK.authenticate(context, oAuthLoginCallback)
                    }
                )
                Spacer(Modifier.width(20.dp))
                SnsIconButton(
                    resourceId = R.drawable.ic_kakao_login_icon,
                    onClick = {
                        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                            if (error != null) {
                                Log.e("KAKAO", "카카오계정으로 로그인 실패", error)
                            } else if (token != null) {
                                getKakaoInfo(UserApiClient.instance, navController)
                            }
                        }
                        UserApiClient.instance.loginWithKakaoAccount(
                            context,
                            callback = callback
                        )
                    }
                )
                Spacer(Modifier.width(20.dp))
                SnsIconButton(
                    resourceId = R.drawable.ic_google_login_icon,
                    onClick = {
                        val googleSignInClient = getGoogleLoginAuth(context)
                        startForResult.launch(googleSignInClient.signInIntent)
                    }
                )
            }
            // to here
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}

private fun getKakaoInfo(
    userApiClient: UserApiClient,
    navController: NavController
) {
    userApiClient.me { user, error ->
        if (error != null) {
            Log.e("KAKAO", "사용자 정보 요청 실패", error)
        } else if (user != null) {
            val nickname = user.kakaoAccount?.profile?.nickname!!
            val email = user.kakaoAccount?.email!!
            //infoList["birthday"] = user.kakaoAccount?.birthday!!
            //infoList["gender"] = user.kakaoAccount?.gender!!.toString()
            navController.navigate(
                "${Route.Joinin.route}?" +
                        "email=${email}" +
//                        "&gender=${gender}" +
//                        "&birthday=${birthday}" +
                        "&nickname=${nickname}"
            )
            userApiClient.unlink {}
        }
    }
}

private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): String? {
    try {
        val account = completedTask.getResult(ApiException::class.java)

        // Signed in successfully, show authenticated UI.
        if (account != null) {
            return account.email!!
        }

        return null
    } catch (e: ApiException) {
        // The ApiException status code indicates the detailed failure reason.
        // Please refer to the GoogleSignInStatusCodes class reference for more information.
        Log.w("GOOGLE", "signInResult:failed code=" + e.statusCode)
        return null
    }
}

private fun getGoogleLoginAuth(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(Constants.GOOGLE_ID)
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(context.getActivity(), gso)
}