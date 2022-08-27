package com.example.wifood.presentation.view.login.new_compose_views

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.view.login.SignUpEvent
import com.example.wifood.presentation.view.login.SignUpViewModel
import com.example.wifood.presentation.view.login.component.TitleText
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.ui.theme.fontMiddleSchool
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.util.composableActivityViewModel
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FindMyLocationView(
    navController: NavController,
    viewModel: SignUpViewModel = composableActivityViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 63.dp)
        ) {
            CustomTextField(
                address = state.address,
                onValueChanged = {
                    viewModel.onEvent(SignUpEvent.AddressChanged(it))
                },
                onDeleteClicked = {
                    viewModel.onEvent(SignUpEvent.AddressChanged(""))
                },
                onSearchClicked = {
                    viewModel.onEvent(SignUpEvent.ButtonClicked)
                }
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.searchResults.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column() {
                                TitleText(
                                    text = "검색 결과가 없습니다.",
                                    color = Color.Black
                                )
                            }
                        }
                    }
                } else {
                    items(state.searchResults) { item ->
                        SearchPlaceInfoCard(
                            item.fullAddress,
                            item.name,
                            state.address
                        ) {
                            viewModel.onEvent(SignUpEvent.AddressClicked(item.fullAddress))
                            navController.navigateUp()
                        }
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 20.dp)
//                            .clickable {
//                                viewModel.onEvent(SignUpEvent.AddressClicked(it.fullAddress))
//                                navController.navigateUp()
//                            }
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.Start,
//                            modifier = Modifier.padding(horizontal = 24.dp)
//                        ) {
//                            Row {
//                                Text(text = it.name, fontSize = 16.sp)
//                                Text(text = it.bizName)
//                            }
//                            Text(text = it.fullAddress)
//                        }
//                    }
                    }
                }
            }
        }
    }

}

@Composable
private fun CustomTextField(
    address: String,
    onValueChanged: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    onSearchClicked: () -> Unit
) {
    TextField(
        value = address,
        onValueChange = onValueChanged,
        leadingIcon = {
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
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(18),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFF6F6F6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            if (address.isNotBlank()) {
                IconButton(
                    onClick = onDeleteClicked,
                    modifier = Modifier.wrapContentSize()
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_delete_text),
                        contentDescription = "",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified
                    )
                }
            }
        }
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