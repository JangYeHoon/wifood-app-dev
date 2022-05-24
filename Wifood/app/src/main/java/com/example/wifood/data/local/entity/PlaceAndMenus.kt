package com.example.wifood.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PlaceAndMenus(
    @Embedded val place: PlaceEntity,

    @Relation(
        parentColumn = "placeId",
        entityColumn = "parentPlaceId"
    )
    val menus: List<MenuGradeEntity>
)
