package com.example.wifood.presentation.view.login.new_compose_views

import androidx.compose.foundation.layout.*
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
import com.example.wifood.R
import com.example.wifood.ui.theme.fontMiddleSchool
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor

@Composable
fun FindMyLocationView(
){
    val scaffoldState = rememberScaffoldState()

    var searchText by remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 63.dp)
        ) {
            CustomTextField()
            Spacer(Modifier.height(12.dp))
            SearchPlaceInfoCard()
            SearchPlaceInfoCard()
            SearchPlaceInfoCard()
            SearchPlaceInfoCard()
        }
    }

}

@Composable
private fun CustomTextField(
) {
    var searchText = ""
    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
        },
        leadingIcon = {
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_search_icon),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        shape = RoundedCornerShape(18),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFF6F6F6),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                  searchText = "강남역"
                },
                modifier = Modifier.wrapContentSize()
            ){
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_delete_text),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
            }
        }
    )
}

@Composable
fun SearchPlaceInfoCard(
    doText:String = "강원도",
    siText:String = "강릉시",
    dongText:String = "중앙동",
    detailText:String = "서울 강남구 역삼동 858"
){
    val scrollState = rememberScrollState() // for horizontal mode screen
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(start = 6.dp)
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                ImageVector.vectorResource(id = R.drawable.ic_place_example_image),
                contentDescription = "",
                modifier = Modifier.wrapContentSize(),
                tint = Color.Unspecified
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = buildAnnotatedString {
                    append(doText + " " + siText + " ")
                    withStyle(
                        style = SpanStyle(
                            fontFamily = mainFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MainColor
                        ),
                    ) {
                        append(dongText)
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
            text = detailText,
            color = Gray03Color,
            fontFamily = mainFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 18.dp)
        )
    }
}