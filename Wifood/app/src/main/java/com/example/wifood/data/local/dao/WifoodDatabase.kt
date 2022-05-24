package com.example.wifood.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.wifood.data.local.util.Converters
import com.example.wifood.domain.model.*

@Database(
    entities = [Group::class, MenuGrade::class, Place::class, Taste::class, User::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WifoodDatabase : RoomDatabase() {

    abstract val wifoodDao: WifoodDao

    companion object {
        const val DATABASE_NAME = "db"
    }
}