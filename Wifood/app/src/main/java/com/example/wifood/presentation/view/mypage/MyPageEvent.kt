package com.example.wifood.presentation.view.mypage

import com.example.wifood.presentation.view.login.SignUpEvent

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
}
