package com.example.wifood.presentation.view.login.new_compose_views

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.SignUpViewModel
import com.example.wifood.presentation.view.mypage.MyPageEvent
import com.example.wifood.presentation.view.mypage.MyPageViewModel
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.util.composableActivityViewModel
import com.example.wifood.view.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchUserAddressView(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    viewModel: SignUpViewModel = composableActivityViewModel(),
    viewModel2: MyPageViewModel = hiltViewModel()
) {
    var view by remember {
        mutableStateOf("")
    }
    view =
        if (navBackStackEntry.arguments?.getString("viewModel")!! == "modify") "modify" else "signup"
    val state = viewModel.state.value
    val state2 = viewModel2.state.value
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomTextField(
                address = if (view == "signup") state.address else state2.address,
                onValueChanged = {
                    if (view == "signup") viewModel.onEvent(SignUpEvent.AddressChanged(it)) else viewModel2.onEvent(
                        MyPageEvent.AddressChanged(it)
                    )
                },
                onBackClicked = {
                    navController.navigateUp()
                },
                onDeleteClicked = {
                    if (view == "signup") viewModel.onEvent(SignUpEvent.AddressChanged("")) else viewModel2.onEvent(
                        MyPageEvent.AddressChanged("")
                    )
                },
                onSearchClicked = {
                    if (view == "signup") viewModel.onEvent(SignUpEvent.ButtonClicked) else viewModel2.onEvent(
                        MyPageEvent.ButtonClicked
                    )
                },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (view == "signup") viewModel.onEvent(SignUpEvent.ButtonClicked) else viewModel2.onEvent(
                            MyPageEvent.ButtonClicked
                        )
                        keyboardController?.hide()
                    }
                )
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = sidePaddingValue.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                if (state.searchResults.isEmpty()) {
//                    item {
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Column() {
//                                Text(
//                                    text = "",
//                                    fontFamily = mainFont,
//                                    fontWeight = FontWeight.Normal,
//                                    fontSize = 16.sp,
//                                    color = EnableColor
//                                )
//                            }
//                        }
//                    }
//                } else {
                if (view == "signup") {
                    items(state.searchResults) { item ->
                        SearchPlaceInfoCard(
                            item.fullAddress,
                            item.name,
                            state.address
                        ) {
                            viewModel.onEvent(SignUpEvent.AddressClicked(item.fullAddress))
                            navController.navigateUp()
                        }
                    }
                } else {
                    items(state2.searchResults) { item ->
                        SearchPlaceInfoCard(
                            item.fullAddress,
                            item.name,
                            state2.address
                        ) {
                            viewModel2.onEvent(MyPageEvent.AddressClicked(item.fullAddress))
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun CustomTextField(
    address: String,
    onValueChanged: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onBackClicked: () -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeholder: String = "동명(읍,면)으로 검색"
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    TextField(
        value = address,
        onValueChange = onValueChanged,
        leadingIcon = {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp),
        shape = RoundedCornerShape(18),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = EnableColor,
            unfocusedIndicatorColor = EnableColor,
            textColor = Black2Color,
            placeholderColor = EnableColor
        ),
        textStyle = TextStyle(
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Gray01Color
        ),
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = EnableColor
            )
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (address.isNotBlank()) {
                    /*IconButton(
                        onClick = onDeleteClicked,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.ic_delete_text),
                            contentDescription = "",
                            modifier = Modifier.wrapContentSize(),
                            tint = Color.Unspecified
                        )
                    }*/
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_delete_text),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                onDeleteClicked()
                            },
                        tint = Color.Unspecified
                    )
                }
                IconButton(
                    onClick = onSearchClicked,
                    modifier = Modifier.wrapContentSize()
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_search_icon),
                        contentDescription = "",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = keyboardActions
    )
}


@Composable
fun SearchPlaceInfoCard(
    address: String,
    name: String,
    search: String,
    onClick: () -> Unit
) {
    val fullAddress = address.split(' ')
    Column(
        modifier = Modifier
            .padding(start = 6.dp)
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_place_example_image),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = buildAnnotatedString {
                    fullAddress.forEach {
                        if (it == search) {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = mainFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = MainColor
                                ),
                            ) {
                                append("$it ")
                            }
                        } else append("$it ")
                    }

                },
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Gray01Color
            )
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = name,
            color = Gray03Color,
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 18.dp)
        )
    }
}