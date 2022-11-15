package com.yogo.wifood.presentation.util

sealed class Route(val route: String) {
    object Main : Route("mainview")
    object Map : Route("mapview")
    object PlaceList : Route("placelistview")
    object MyPage : Route("mypageview")
    object PlaceInfo : Route("placeinfoview")
    object Joinin : Route("joininview")
    object EditPlace : Route("editplaceview")
    object PlaceInputNameAndVisited : Route("placeinputnameandvisited")
    object PlaceInputStarAndEvaluation : Route("placeinputstarandevaluation")
    object PlaceInputMenuEvaluation : Route("placeinputmenu")
    object PlaceInputReviewAndImages : Route("placeinputreviewandimages")
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
    object GetPhoneNumber : Route("getphonenumber")
    object GetAuthNumber : Route("getauthnumber")
    object Agreement : Route("agreement")
    object FindLocation : Route("findlocation")
    object SignUp3 : Route("signup3")
    object SignUp4 : Route("signup4")
    object SignUp5 : Route("signup5")
    object GetFavor : Route("getfavor")
    object Complete : Route("complete")
    object MapSearchAddress : Route("mapsearchaddress")
    object AddNewPlaceComplete : Route("addnewplacecomplete")
    object ModifyPhoneNumber : Route("modifyphonenumber")
    object ModifyAddress : Route("modifyaddress")
    object ModifyFavor : Route("modifyfavor")
    object ServiceUsingAgreement : Route("serviceusingagreement")
    object DeveloperInfo : Route("developerinfo")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}