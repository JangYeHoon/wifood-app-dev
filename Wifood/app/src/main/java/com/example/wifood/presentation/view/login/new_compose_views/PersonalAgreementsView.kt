package com.example.wifood.presentation.view.login.new_compose_views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.*

@Preview(showBackground = true)
@Composable
fun PersonalAgreementsView(

){
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState() // for horizontal mode screen
    var isAgreeClicked by remember { mutableStateOf(false)}

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = sidePaddingValue.dp)
        ){
            Spacer(Modifier.height(106.dp))
            Text(
                text = "인증번호 4자리를\n입력해주세요.",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(48.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                IconButton(
                    onClick = {
                        isAgreeClicked.not()
                    },
                    modifier = Modifier
                        .wrapContentSize()
                ){
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_check_button),
                        contentDescription = "",
                        modifier = Modifier.wrapContentSize(),
                        tint = if (isAgreeClicked) MainColor else Color.Unspecified,
                    )
                }
                Spacer(Modifier.width(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                ){
                    Text(
                        text = "이용약관 동의(필수)",
                        fontFamily = mainFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Gray01Color
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_right_arrow_boxed),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified,
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            MainButton(
                text = "다음",
                onClick = {

                },
                activate = isAgreeClicked
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}