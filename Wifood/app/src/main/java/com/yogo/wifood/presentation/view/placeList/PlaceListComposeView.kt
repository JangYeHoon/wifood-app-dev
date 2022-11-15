package com.yogo.wifood.presentation.view.placeList

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.view.component.YOGOTopAppBar
import com.yogo.wifood.presentation.view.main.MainEvent
import com.yogo.wifood.presentation.view.main.MainViewModel
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.R
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.presentation.view.component.DoubleButtonTopAppBar
import com.yogo.wifood.presentation.view.main.MainState
import com.google.gson.Gson
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
    Column{
        DoubleButtonTopAppBar(
            leftButtonOn = false,
            leftButtonClicked = {
                navController.popBackStack()
            },
            rightButtonClicked = {
                val groupJson = Uri.encode(Gson().toJson(Group()))
                navController.navigate("${Route.GroupNameInput.route}/$groupJson")
            }
        )
        // Main Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F6F7))
                .verticalScroll(scrollState, true)
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
                        explainText = group.description,
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

@ExperimentalCoilApi
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
                    val placeListItems =
                        targetState!!.places.filter { it.groupId == targetGroup!!.groupId }
                    val placeListItemLength = placeListItems.size
                    var placeListCount = 0
                    targetState!!.places.filter { it.groupId == targetGroup!!.groupId }
                        .forEach { place ->
                            PlaceListItem(
                                itemTitle = place.name,
                                itemExplain = place.bizName,
                                itemIcon = rememberImagePainter(
                                    data =
                                    if (targetState.placeImages.contains(place.placeId))
                                        targetState.placeImages[place.placeId]
                                    else
                                        R.drawable.place_list_item_default
                                )
                            ) {
                                val placeJson = Uri.encode(Gson().toJson(place))
                                val groupJson = Uri.encode(Gson().toJson(targetGroup))
                                navController.navigate("${Route.PlaceInfo.route}/${placeJson}/${groupJson}")
                            }
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

@ExperimentalCoilApi
@Composable
fun PlaceListItem(
    itemTitle: String = "회사 근처 카페",
    itemExplain: String = "강남 카페",
    itemIcon: ImagePainter = rememberImagePainter(data = R.drawable.place_list_item_default),
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
            painter = itemIcon,
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
