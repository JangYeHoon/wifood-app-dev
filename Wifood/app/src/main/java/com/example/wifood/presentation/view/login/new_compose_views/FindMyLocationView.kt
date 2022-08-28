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
import androidx.compose.ui.text.TextStyle
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
import com.example.wifood.view.ui.theme.*

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
        ) {
            CustomTextField(
                address = state.address,
                onValueChanged = {
                    viewModel.onEvent(SignUpEvent.AddressChanged(it))
                },
                onBackClicked = {
                    // TODO
                },
                onDeleteClicked = {
                    viewModel.onEvent(SignUpEvent.AddressChanged(""))
                },
                onSearchClicked = {
                    viewModel.onEvent(SignUpEvent.ButtonClicked)
                },
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = sidePaddingValue.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.searchResults.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column() {
                                Text(
                                    text = "",
                                    fontFamily = mainFont,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                    color = EnableColor
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
    onSearchClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
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
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Gray01Color
        ),
        placeholder = {
            Text(
                text = "동명(읍,면)으로 검색",
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = EnableColor
            )
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
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