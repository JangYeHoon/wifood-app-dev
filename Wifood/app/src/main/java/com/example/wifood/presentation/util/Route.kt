package com.example.wifood.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(val route: String) {
    object Main : Route("mainview")
    object Login : Route("loginview")
    object Map : Route("mapview")
    object PlaceList : Route("placelistview")
    object MyPage : Route("mypageview")
    object PlaceInfo : Route("placeinfoview")
    object Joinin : Route("joininview")
    object EditPlace : Route("editplaceview")
    object PlaceInputNameAndVisited : Route("placeinputnameandvisited")
    object PlaceInputStarAndEvaluation : Route("placeinputstarandevaluation")
    object PlaceInputImagesAndMenuEvaluation : Route("placeinputimageandmenu")
    object PlaceInputReview : Route("placeinputreview")
    object Search : Route("searchplaceview")
    object EditProfile : Route("editprofileview")
    object MobileAuthentication : Route("mobileauthenticationview")
    object Splash : Route("splashview")
    object Onboarding : Route("onboardingview")
    object GroupNameInput : Route("groupnameinputview")
    object GroupDescInput : Route("groupdescinputview")
    object GroupEdit : Route("groupeditview")
    object GetUserFavor : Route("getuserfavorview")
    object FindPwd : Route("findpwdview")
    object EditMyInfo : Route("editmyinfocomposeview")
    object AppInfo : Route("appinfoview")
}