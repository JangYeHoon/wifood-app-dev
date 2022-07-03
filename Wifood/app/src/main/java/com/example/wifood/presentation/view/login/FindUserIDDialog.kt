package com.example.wifood.presentation.view.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wifood.presentation.view.login.component.FindIdPwdButton
import com.example.wifood.presentation.view.login.join.GetUserFavorContent
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.Gray01Color


@ExperimentalComposeUiApi
@Composable
fun FindUserIDDialog(
    showDialog:MutableState<Boolean>,
    userId:String = "hyjung@yogo.com"
){
    Dialog(
        onDismissRequest = {
            showDialog.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        FindUserIDDialogContent(userId)
    }
}

@Preview(showBackground = true)
@Composable
fun FindUserIDDialogContent(
    userId:String = "hyjung@yogo.com"
){
    Surface(
        modifier = Modifier
            .width(312.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFFFFFFF)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 34.dp)
        ){
            Text(
                text = "아이디 안내",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.Black,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = userId,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Gray01Color,
            )
            Spacer(Modifier.height(10.dp))
            Spacer(Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                FindIdPwdButton(
                    text = "비밀번호 변경",
                    onClick = {},
                    inverse = true
                )
                FindIdPwdButton(
                    text = "로그인 하기",
                    onClick = {}
                )
            }
            Spacer(Modifier.height(18.dp))
        }
    }
}