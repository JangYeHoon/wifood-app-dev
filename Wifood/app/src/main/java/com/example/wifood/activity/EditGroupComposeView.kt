package com.example.wifood.activity

import android.content.Intent
import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.wifood.R
import com.example.wifood.viewmodel.GroupComposeViewModel
import com.example.wifood.viewmodel.GroupViewModel
import java.nio.file.Files.size

class EditGroupComposeView : ComponentActivity() {
    private val viewModel by viewModels<GroupComposeViewModel>()
    private val colorList = listOf(
        Color(0xFFFF9800),
        Color(0xFF009688),
        Color(0xFF4CAF50),
        Color(0xFFCDDC39),
        Color(0xFFFFC107),
        Color(0xFFF44336),
        Color(0xFF9C27B0),
        Color(0xFF3F51B5),
        Color(0xFFE91E63),
        Color(0xFFEEDFCC)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = intent.getStringExtra("type") as String
        if (type == "EDIT")
            viewModel.initGroup(intent.getParcelableExtra("group")!!)
        Log.d("composeViewLog", colorList[0].toString())
        setContent {
            Screen(viewModel, colorList)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Screen(viewModel: GroupComposeViewModel, colors: List<Color>) {
    Column {
        TextField(
            value = viewModel.name.value,
            singleLine = true,
            onValueChange = viewModel::updateGroupName
        )

        TextField(
            value = viewModel.theme.value,
            singleLine = true,
            onValueChange = viewModel::updateGroupTheme
        )

        /* TODO: 핀 선택 시 선택한 핀만 커지도록 변경 */
        LazyVerticalGrid(cells = GridCells.Fixed(5)) {
            items(colors) { color ->
                Button(
                    onClick = {
                        viewModel.updatePinByColor(color)
                    },
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_group_pin),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color),
                    )
                }
            }
        }

        Button(
            onClick = {
                Log.d("groupComposeView", viewModel.getGroupInstance().toString())
            }
        ) {
            Text("저장")
        }
    }
}