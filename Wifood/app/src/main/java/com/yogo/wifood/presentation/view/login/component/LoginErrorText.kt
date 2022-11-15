package com.yogo.wifood.presentation.view.login.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.*
import com.yogo.wifood.R

@Composable
fun LoginErrorText(
    ErrorText:String = "에러",
    visibility:Boolean = false
){
    if (visibility) {
        Column(
            modifier = Modifier
                .padding(horizontal = sidePaddingValue.dp)
                .padding(top = 26.dp)
        ){
            TextButton(
                shape = RoundedCornerShape(16.dp),
                onClick = {},
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFFF3ED)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
            )
            {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_error_text_icon),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(7.dp))
                Text(
                    text = ErrorText,
                    color = MainColor,
                    fontSize = 12.sp,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
}