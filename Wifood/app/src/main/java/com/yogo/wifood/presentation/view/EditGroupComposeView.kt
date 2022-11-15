package com.yogo.wifood.presentation.view
//
//import androidx.compose.ui.graphics.Color
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.viewModels
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.lazy.GridCells
//import androidx.compose.foundation.lazy.LazyVerticalGrid
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.res.painterResource
//import com.yogo.wifood.R
//import com.yogo.wifood.domain.model.Group
//import com.yogo.wifood.presentation.viewmodel.GroupComposeViewModel
//
//class EditGroupComposeView : ComponentActivity() {
//    private val viewModel by viewModels<GroupComposeViewModel>()
//    private val colorList = listOf(
//        Color(0xFFFF9800),
//        Color(0xFF009688),
//        Color(0xFF4CAF50),
//        Color(0xFFCDDC39),
//        Color(0xFFFFC107),
//        Color(0xFFF44336),
//        Color(0xFF9C27B0),
//        Color(0xFF3F51B5),
//        Color(0xFFE91E63),
//        Color(0xFFEEDFCC)
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val type = intent.getStringExtra("type") as String
////        var group = Group()
////        if (type == "EDIT")
////            group = intent.getParcelableExtra("group")!!
////        setContent {
////            Screen(viewModel, colorList, group)
////        }
//    }
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun Screen(viewModel: GroupComposeViewModel, colors: List<Color>, group: Group) {
//
//    LaunchedEffect(key1 = true) {
////        viewModel.initGroup(group)
//    }
//
//    Column {
//        TextField(
//            value = viewModel.name.value,
//            singleLine = true,
//            onValueChange = viewModel::updateGroupName
//        )
//
//        TextField(
//            value = viewModel.theme.value,
//            singleLine = true,
//            onValueChange = viewModel::updateGroupTheme
//        )
//
//        /* TODO: 핀 선택 시 선택한 핀만 커지도록 변경 */
//        LazyVerticalGrid(cells = GridCells.Fixed(5)) {
//            items(colors) { color ->
//                Button(
//                    onClick = {
//                        viewModel.updatePinByColor(color)
//                    },
//                ) {
//                    Image(
//                        painterResource(id = R.drawable.ic_group_pin),
//                        contentDescription = "",
//                        colorFilter = ColorFilter.tint(color),
//                    )
//                }
//            }
//        }
//
//        Button(
//            onClick = {
//                // TODO "DB에 Group 추가하고 페이지 이동"
//            }
//        ) {
//            Text("저장")
//        }
//    }
//}