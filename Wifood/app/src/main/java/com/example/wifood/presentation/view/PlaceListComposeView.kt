package com.example.wifood.presentation.view

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.wifood.R
import com.example.wifood.presentation.view.component.BottomSheetContent
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview
@Composable
fun PlaceListComposeView() {
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var visible by remember {
        mutableStateOf(false)
    }
    var initallyVisible by remember {
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

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent()
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color(0xFF222222)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "?????? ?????????")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.Menu, "menu")
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigation() {
                    val selectedIndex = remember { mutableStateOf(0) }
                    BottomNavigation(elevation = 10.dp) {

                        BottomNavigationItem(icon = {
                            Icon(imageVector = Icons.Default.Home, "")
                        },
                            label = { Text(text = "Home") },
                            selected = (selectedIndex.value == 0),
                            onClick = {
                                selectedIndex.value = 0
                            }
                        )

                        BottomNavigationItem(icon = {
                            Icon(imageVector = Icons.Default.Favorite, "")
                        },
                            label = { Text(text = "Favorite") },
                            selected = (selectedIndex.value == 1),
                            onClick = {
                                selectedIndex.value = 1
                            }
                        )

                        BottomNavigationItem(icon = {
                            Icon(imageVector = Icons.Default.Person, "")
                        },
                            label = { Text(text = "Profile") },
                            selected = (selectedIndex.value == 2),
                            onClick = {
                                selectedIndex.value = 2
                            }
                        )
                    }
                }
            }
        ) {
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
                    items(listOf("??????", "???????????????", "????????? ??????")) {
                        Card(
                            modifier = Modifier
                                .width(370.dp)
                                .height(86.dp)
                                .padding(8.dp),
                            elevation = 10.dp
                        ) {
                            Column {
                                Row() {
                                    Text(text = it)
                                    IconButton(onClick = {
                                        visible = true
                                    }) {
                                        Icon(Icons.Filled.ArrowDropDown, "menu")
                                    }
                                    Spacer(modifier = Modifier.width(250.dp))
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
                                Text(text = "?????? ?????? ??????")
                                AnimatedVisibility(
                                    visible = visible,
                                    initiallyVisible = initallyVisible,
                                    enter = enterExpand + enterFadeIn,
                                    exit = exitCollapse + exitFadeOut
                                ) {
                                    Text(text = "??????!")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}