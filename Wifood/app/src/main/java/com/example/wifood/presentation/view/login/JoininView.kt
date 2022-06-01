package com.example.wifood.presentation.view.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.view.component.MainButton
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.component.navigationBackButton
import com.example.wifood.presentation.view.login.component.*
import com.example.wifood.ui.theme.fontRoboto
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.view.ui.theme.DividerColor
import com.example.wifood.view.ui.theme.Gray01Color
import com.example.wifood.view.ui.theme.Gray03Color
import com.example.wifood.view.ui.theme.MainColor

fun JoinView(navController: NavController) {

}

//@Preview(showBackground = true)
@Composable
fun JoininContent() {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var is_agreement:Boolean = false
    Scaffold(
        topBar = {
            YOGOTopAppBar(
                text="회원가입",
                onBackButtonClicked={/*TODO*/}
            )
        }
    ) {
        Column(
            Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(31.dp))
            // set id
            TitleText("아이디")
            Spacer(Modifier.height(5.dp))
            //ExplainText("영어와 숫자를 포함한 8자리 이상으로 입력해주세요")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = "",/*TODO*/
                placeholder = "아이디",
                onValueChange = {/*TODO*/ }
            )
            Spacer(Modifier.height(20.dp))

            // Set password
            TitleText("비밀번호")
            Spacer(Modifier.height(5.dp))
            ExplainText("영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = "",/*TODO*/
                placeholder = "비밀번호",
                onValueChange = {/*TODO*/ }
            )
            Spacer(Modifier.height(20.dp))

            // Set password check
            TitleText("비밀번호 확인")
            Spacer(Modifier.height(5.dp))
            //ExplainText("")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = "",/*TODO*/
                placeholder = "비밀번호 확인",
                onValueChange = {/*TODO*/ }
            )
            Spacer(Modifier.height(20.dp))

            // Set nickname
            TitleText("닉네임")
            Spacer(Modifier.height(5.dp))
            ExplainText("다른 유저와 겹치지 않는 닉네임을 입력해주세요 (2~15자)")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = "",/*TODO*/
                placeholder = "닉네임 (2~15자)",
                onValueChange = {/*TODO*/ }
            )
            Spacer(Modifier.height(20.dp))

            // Set phone check
            TitleText("본인인증")
            Spacer(Modifier.height(5.dp))
            //ExplainText("")
            Spacer(Modifier.height(5.dp))
            Box(modifier = Modifier.width(312.dp)) {
                InputTextField(
                    text = "",/*TODO*/
                    placeholder = "닉네임 (2~15자)",
                    onValueChange = {/*TODO*/ },
                )
                TextInsideButton(
                    text = "인증번호 받기",
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {},
                )
            }
            InputTextField(
                text = "",/*TODO*/
                placeholder = "인증번호 입력 (3분 이내)",
                onValueChange = {/*TODO*/ },
            )
            Spacer(Modifier.height(20.dp))

            // Set address
            TitleText("주소")
            Spacer(Modifier.height(5.dp))
            //ExplainText("")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = "",/*TODO*/
                placeholder = "주소 검색",
                onValueChange = {/*TODO*/ },
            )
            Spacer(Modifier.height(5.dp))
            ExplainText("상세주소")
            Spacer(Modifier.height(5.dp))
            InputTextField(
                text = "",/*TODO*/
                placeholder = "상세 주소를 입력해주세요",
                onValueChange = {/*TODO*/ },
            )
            Spacer(Modifier.height(20.dp))

            Box(modifier = Modifier.width(312.dp)) {
                Row() {
                    Column() {
                        TitleText("생년월일")
                        Spacer(Modifier.height(5.dp))
                        InputTextField(
                            text = "",/*TODO*/
                            placeholder = "YYMMDD",
                            onValueChange = {/*TODO*/ },
                        )
                    }
                    Column() {
                        TitleText("성별")
                        Spacer(Modifier.height(10.dp))
                        Row() {
                            SelectedToggle(
                                text = "남성",
                                onClick = {/*TODO*/ },
                            )
                            Spacer(Modifier.width(10.dp))
                            UnSelectedToggle(
                                text = "여성",
                                onClick = {/*TODO*/ },
                            )
                        }
                        Spacer(Modifier.width(37.dp))
                    }
                }
            }

            Divider(
                color = DividerColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
            )
            Spacer(Modifier.height(30.dp))
            TitleText("약관동의")
            Spacer(Modifier.height(16.dp))
            Row()
            {
                ToggleButton(
                    isChecked = false,
                    onClick = {/*TODO*/ }
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "개인정보수집 및 이용동의",
                    color = Gray01Color,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "(필수)",
                    color = MainColor,
                    modifier = Modifier.padding(top=4.dp),
                    fontFamily = fontRoboto,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = "수집목적 : 정보를 이용한 내용 내용 내용 내용 내용\n보유 및 이용기간 : 회원가입 철회 시 까지",
                Modifier.padding(start = 24.dp),
                color = Gray03Color,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp
            )
            Spacer(Modifier.height(48.dp))

            Row(Modifier.padding(start=51.dp)){
                TransparentButton(
                    text = "정보 더 입력하고 자세한 추천 받기 >",
                    textColor = MainColor,
                    textSize = 12,
                    width = 220,
                    height = 32,
                    onClick = {/*TODO*/ }
                )
            }
            Spacer(Modifier.height(17.dp))
            MainButton(
                text = "회원가입하기",
                onClick = {/*TODO*/ }
            )
        }
    }
}