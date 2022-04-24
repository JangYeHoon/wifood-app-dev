package com.example.wifood.activity

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import com.example.wifood.R
import com.example.wifood.viewmodel.GroupViewModel

class EditGroupComposeView : ComponentActivity() {
    lateinit var groupViewModel: GroupViewModel
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
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        if (type == "EDIT")
            groupViewModel.setGroupInstance(intent.getParcelableExtra("group")!!)
        Log.d("composeViewLog", colorList[0].toString())
        setContent {
            Screen(groupViewModel, colorList)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Screen(viewModel: GroupViewModel, colors: List<Color>) {
    Column {
        TextField(
            value = viewModel.getGroupName(),
            singleLine = true,
            onValueChange = viewModel::setGroupName
        )

        TextField(
            value = viewModel.getGroupTheme(),
            singleLine = true,
            onValueChange = viewModel::setGroupTheme
        )

        LazyVerticalGrid(cells = GridCells.Fixed(5)) {
            items(colors) { color ->
                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painterResource(id = R.drawable.ic_group_pin),
                        contentDescription = "#FF9800",
                        colorFilter = ColorFilter.tint(color)
                    )
                }
            }
        }

        Button(onClick = { /*TODO*/ }) {
            Text("저장")
        }
    }
}