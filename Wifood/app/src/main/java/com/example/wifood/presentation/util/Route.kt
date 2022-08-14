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
    object SignUp1 : Route("signup1")
    object SignUp2 : Route("signup2")
    object SignUp3 : Route("signup3")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}