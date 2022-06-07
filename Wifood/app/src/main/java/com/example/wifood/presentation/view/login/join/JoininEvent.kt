package com.example.wifood.presentation.view.login.join

import com.example.wifood.presentation.view.login.LoginFormEvent

sealed class JoininEvent {
    data class EmailChanged(val email: String) : JoininEvent()
    data class PasswordChanged(val password: String) : JoininEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : JoininEvent()
    data class NicknameChanged(val nickname: String) : JoininEvent()
    data class PhoneChanged(val phoneNumber: String) : JoininEvent()
    data class ValidNChanged(val validNumber: String) : JoininEvent()
    data class AddressChanged(val address: String) : JoininEvent()
    data class DetailedAChanged(val detailedAddress: String) : JoininEvent()
    data class BirthChanged(val birthday: String) : JoininEvent()
    object TermsClicked : JoininEvent()
    object Joinin : JoininEvent()
}
