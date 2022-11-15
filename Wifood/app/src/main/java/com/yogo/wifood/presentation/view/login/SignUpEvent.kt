package com.yogo.wifood.presentation.view.login

sealed class SignUpEvent() {
    data class PhoneNumChanged(val phoneNumber: String) : SignUpEvent()
    data class CertChanged(val certNumber: String) : SignUpEvent()
    data class AddressChanged(val address: String) : SignUpEvent()
    data class BirthdayChanged(val birthday: String) : SignUpEvent()
    object GenderClicked : SignUpEvent()
    object RequestCertNumber : SignUpEvent()
    data class Verify(val certNumber: String, val timer: Int) : SignUpEvent()
    object ShowDocument : SignUpEvent()
    object ButtonClicked : SignUpEvent()
    data class AddressClicked(val address: String? = "") : SignUpEvent()
    object AgreementClicked : SignUpEvent()
    object AgreementDetailClicked : SignUpEvent()
    data class FavorSelected(val selected: Int, val index: Int) : SignUpEvent()
    object TasteCreated : SignUpEvent()
}