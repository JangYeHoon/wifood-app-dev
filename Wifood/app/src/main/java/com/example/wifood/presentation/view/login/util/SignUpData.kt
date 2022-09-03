package com.example.wifood.presentation.view.login.util

import com.example.wifood.domain.model.Taste
import com.example.wifood.domain.model.User

object SignUpData {
    lateinit var user: User
    lateinit var phoneNumber: String
    var address: String = ""
    lateinit var birthday: String
    var gender: String = "남성"
    lateinit var taste: Taste
    var cucumberClicked: Boolean = false
    var corianderClicked: Boolean = false
    var mintChokoClicked: Boolean = false
    var eggplantClicked: Boolean = false
}