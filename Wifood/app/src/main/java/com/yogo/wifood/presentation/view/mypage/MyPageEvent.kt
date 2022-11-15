package com.yogo.wifood.presentation.view.mypage

import android.net.Uri
import com.yogo.wifood.presentation.view.login.SignUpEvent
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInfoWriteFormEvent

sealed class MyPageEvent {
    data class PhoneNumChanged(val phoneNumber: String) : MyPageEvent()
    data class CertChanged(val certNumber: String) : MyPageEvent()
    data class AddressChanged(val address: String) : MyPageEvent()
    data class BirthdayChanged(val birthday: String) : MyPageEvent()
    object GenderClicked : MyPageEvent()
    data class NicknameChanged(val nickname: String) : MyPageEvent()
    object RequestCertNumber : MyPageEvent()
    data class Verify(val certNumber: String, val timer: Int) : MyPageEvent()
    object ShowDocument : MyPageEvent()
    object ButtonClicked : MyPageEvent()
    data class AddressClicked(val address: String? = "") : MyPageEvent()
    object AgreementClicked : MyPageEvent()
    data class FavorSelected(val selected: Int, val index: Int) : MyPageEvent()
    object TasteCreated : MyPageEvent()
    object DeleteUser : MyPageEvent()
    data class ModifyUserInfo(val obj: String) : MyPageEvent()
    data class ImageAdd(val image: Uri) : MyPageEvent()
    object ModifyProfile : MyPageEvent()
}
