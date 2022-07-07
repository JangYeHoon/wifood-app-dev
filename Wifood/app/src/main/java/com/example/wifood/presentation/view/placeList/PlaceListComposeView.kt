package com.example.wifood.presentation.view.placeList

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.wifood.R
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.AnimateVisibility
import com.example.wifood.presentation.view.component.BottomSheetContent
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun PlaceListComposeView(
    modalBottomSheetState: ModalBottomSheetState,
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.state

    val initallyVisible by remember {
        mutableStateOf(false)
    }
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(1000))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(1000))
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.groups) { group ->
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    elevation = 10.dp
                ) {
                    Column {
                        Row() {
                            Text(text = group.name)
                            IconButton(onClick = {
                                viewModel.onEvent(MainEvent.GroupClicked(if (state.selectedGroupId != group.groupId) group.groupId else 0))
                            }) {
                                Icon(Icons.Filled.ArrowDropDown, "menu")
                            }
                            Spacer(modifier = Modifier.width(250.dp))
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(MainEvent.GroupSheetClicked(group))
                                    scope.launch {
                                        modalBottomSheetState.show()
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Menu, "menu")
                            }
                        }
                        Spacer(modifier = Modifier.width(250.dp))
                        AnimatedVisibility(
                            visible = group.groupId == state.selectedGroupId,
                            initiallyVisible = initallyVisible,
                            enter = enterExpand + enterFadeIn,
                            exit = exitCollapse + exitFadeOut
                        ) {
                            Column {
                                state.places.filter { it.groupId == group.groupId }
                                    .forEach { place ->
                                        Row() {
                                            Text(text = place.name)
                                            IconButton(onClick = {
                                                val placeJson = Uri.encode(Gson().toJson(place))
                                                val groupJson = Uri.encode(Gson().toJson(group))
                                                navController.navigate("${Route.PlaceInfo.route}/${placeJson}/${groupJson}")
                                            }) {
                                                Icon(Icons.Filled.ArrowForward, "place")
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}
