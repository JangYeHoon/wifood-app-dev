package com.yogo.wifood.presentation.view.component

import android.view.animation.BounceInterpolator
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yogo.wifood.presentation.util.Route
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AnimateVisibility(
    modalBottomSheetState: ModalBottomSheetState,
//    viewModel: PlaceListViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
//    val visible = viewModel.visible.value

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = "위시리스트")
            IconButton(onClick = {
//                if (visible) viewModel.collapseVisibility() else viewModel.expandVisibility()
            }) {
                Icon(Icons.Filled.ArrowDropDown, "menu")
            }
            Spacer(modifier = Modifier.width(100.dp))
            IconButton(
                onClick = {
                    scope.launch {
                        modalBottomSheetState.show()
                    }
                }
            ) {
                Icon(Icons.Filled.Menu, "menu")
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "회사 근처 맛집")
//        AnimatedVisibility(
//            visible = visible,
//            enter = fadeIn(animationSpec = tween(1000)) +
//                    expandVertically(
//                        animationSpec = tween(
//                            1500
//                        )
//                    ),
//            exit = fadeOut(animationSpec = tween(1000)) +
//                    shrinkVertically(
//                        animationSpec = tween(
//                            1500
//                        )
//                    )
//        ) {
//            Text(
//                text = "짜잔!",
//                modifier = Modifier.clickable {
//                    navController.navigate(Route.PlaceInfo.route)
//                }
//            )
//        }
    }
}