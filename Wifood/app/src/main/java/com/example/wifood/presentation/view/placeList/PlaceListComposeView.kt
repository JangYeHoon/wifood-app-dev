package com.example.wifood.presentation.view.placeList

import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.view.component.YOGOTopAppBar
import com.example.wifood.presentation.view.main.MainEvent
import com.example.wifood.presentation.view.main.MainViewModel
import com.example.wifood.ui.theme.mainFont
import com.example.wifood.R
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.presentation.view.main.MainState
import com.example.wifood.view.ui.theme.Enable
import com.example.wifood.view.ui.theme.EnableColor
import com.example.wifood.view.ui.theme.Gray03Color
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
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val cardInterval = 10
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            YOGOTopAppBar(
                text = "맛집 리스트",
                leftButtonOn = true,
                leftButtonClicked = {
                    navController.popBackStack()
                },
                rightButtonOn = true,
                rightButtonClicked = {
                    //navController.navigate(Route.)
                }
            )
        }
    ) {

        // Main Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F6F7))
                .verticalScroll(scrollState,true)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .padding(vertical = cardInterval.dp),
            ) {
                state.groups.forEach { group ->
                    PlaceInfoCard(
                        placeText = group.name,
                        listButtonClicked = {
                            viewModel.onEvent(MainEvent.GroupClicked(if (state.selectedGroupId != group.groupId) group.groupId else 0))
                        },
                        optionIconClicked = {
                            viewModel.onEvent(MainEvent.GroupSheetClicked(group))
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        },
                        explainText = "디저트 카페",
                        targetState = state,
                        targetGroup = group,
                        navController = navController
                    )
                    Spacer(Modifier.height(cardInterval.dp))
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun PlaceInfoCard(
    placeText: String = "위시리스트",
    explainText: String = "디저트 카페",
    listButtonClicked: () -> Unit = {},
    optionIconClicked: () -> Unit = {},
    targetState: MainState? = null,
    targetGroup: Group? = null,
    navController: NavController,
) {
    var isCardOpen = remember { mutableStateOf(false) }
    var paddingValue = 24
    val roundedCornerValue = 10

    val places: List<Place> = emptyList()

    paddingValue = if (isCardOpen.value) {
        18
    } else {
        24
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundedCornerValue.dp))
            .background(color = Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = paddingValue.dp)
                .padding(vertical = paddingValue.dp)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    listButtonClicked()
                    isCardOpen.value = !isCardOpen.value
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = placeText,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF000000),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(Modifier.width(5.dp))
                Icon(
                    ImageVector.vectorResource(id = if (isCardOpen.value) R.drawable.ic_place_list_close_icon else R.drawable.ic_place_list_open_icon),
                    contentDescription = "place list open close icon",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically),
                    tint = androidx.compose.ui.graphics.Color.Unspecified
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = optionIconClicked,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically)
                )
                {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_place_list_option_button),
                        contentDescription = "left button of top app bar",
                        modifier = Modifier.wrapContentSize(),
                        tint = androidx.compose.ui.graphics.Color.Unspecified
                    )
                }
            }

            AnimatedVisibility(
                visible = isCardOpen.value,
            ) {
                Column() {
                    Spacer(Modifier.height(8.dp))
                    val placeListItems = targetState!!.places.filter { it.groupId == targetGroup!!.groupId }
                    val placeListItemLength = placeListItems.size
                    var placeListCount = 0
                    targetState!!.places.filter { it.groupId == targetGroup!!.groupId }
                        .forEach { place ->
                            PlaceListItem(
                                itemTitle = place.name,
                                itemExplain = "강남 카페",
                                itemIcon = R.drawable.place_list_item_default,
                                itemClicked = {
                                    val placeJson = Uri.encode(Gson().toJson(place))
                                    val groupJson = Uri.encode(Gson().toJson(targetGroup))
                                    navController.navigate("${Route.PlaceInfo.route}/${placeJson}/${groupJson}")
                                }
                            )
                            if (placeListCount != (placeListItemLength - 1)) // dont append space at last
                                Spacer(Modifier.height(18.dp))
                            placeListCount += 1
                        }

                }
            }
            AnimatedVisibility(
                visible = !isCardOpen.value,
            ) {
                Text(
                    text = explainText,
                    fontFamily = mainFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color(0xFF000000)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceListItem(
    itemTitle: String = "회사 근처 카페",
    itemExplain: String = "강남 카페",
    itemIcon: Int = R.drawable.place_list_item_default,
    itemClicked: () -> Unit = {}
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = Modifier.clickable(
            //indication = null,
            //interactionSource = interactionSource
        ) {
            itemClicked()
        }
    ) {
        Image(
            // profile image
            painter = painterResource(
                id = itemIcon
            ),
            contentDescription = "",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.width(14.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = itemTitle,
                fontFamily = mainFont,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF000000)
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = itemExplain,
                fontFamily = mainFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFF000000)
            )
        }
        Spacer(Modifier.weight(1f))
    }
}
