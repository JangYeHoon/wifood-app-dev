package com.yogo.wifood.presentation.view.login.contents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.presentation.view.component.MainButton
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*


//@Preview(showBackground = true)
@Composable
fun GetUserAgreementContent(
    onAgreeClicked: () -> Unit = {},
    agreeChecked: Boolean = false,
    showAgreementDialog: MutableState<Boolean> = mutableStateOf(false),
    onButtonClicked: () -> Unit = {}
){
    val scrollState = rememberScrollState() // for horizontal mode screen
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = sidePaddingValue.dp)
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                text = "이용약관에\n동의해주세요",
                fontFamily = mainFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black2Color
            )
            Spacer(Modifier.height(48.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onAgreeClicked,
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Icon(
                        ImageVector.vectorResource(id = if (agreeChecked) R.drawable.ic_checked_color else R.drawable.ic_check_button),
                        contentDescription = "",
                        modifier = Modifier.wrapContentSize(),
                        tint = Color.Unspecified,
                    )
                }
                Spacer(Modifier.width(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ) {
                            showAgreementDialog.value = true
                        }
                ) {
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
                onClick = onButtonClicked,
                activate = agreeChecked
            )
            Spacer(Modifier.height(buttonBottomValue.dp))
        }
    }
}