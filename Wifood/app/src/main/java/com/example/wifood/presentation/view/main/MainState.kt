package com.example.wifood.presentation.view.main

import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.MenuGrade
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User
import com.example.wifood.presentation.util.NavItem

data class MainState(
    val user: User? = null,
    val groups: MutableMap<Int, List<Group>> = mutableMapOf(),
    val places: MutableMap<Int, List<Place>> = mutableMapOf(),
    val menus: MutableMap<Int, List<MenuGrade>> = mutableMapOf(),
    val selected: String = NavItem.Map.id
)
