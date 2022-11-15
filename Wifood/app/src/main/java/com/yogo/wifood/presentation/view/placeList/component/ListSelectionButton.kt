package com.yogo.wifood.presentation.view.placeList.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.R
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*

@Composable
fun ListSelectionButtonWithIcon(
    buttonText: String = "그룹 선택",
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ){
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            MainColor
                        ),
                    ) {
                        append("*")
                    }
                    append(" ")
                    append(buttonText)
                },
                color = Gray03Color,
                fontSize = 15.sp,
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            Icon(
                ImageVector.vectorResource(R.drawable.ic_right_arrow),
                contentDescription = "back button",
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                tint = Color.Unspecified
            )
        }
        Divider(
            color = PlaceInfoWriteDividerColor,
            modifier = Modifier.height(1.dp)
        )
    }
}

@Composable
fun ListSelectionButtonWithoutIcon(
    buttonText: String = "그룹 선택",
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
    ){
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            MainColor
                        ),
                    ) {
                        append("*")
                    }
                    append(" ")
                    append(buttonText)
                },
                color = Gray03Color,
                fontSize = 15.sp,
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            color = PlaceInfoWriteDividerColor,
            modifier = Modifier.height(1.dp)
        )
    }
}