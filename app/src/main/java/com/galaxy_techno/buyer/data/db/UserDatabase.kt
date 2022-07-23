package com.galaxy_techno.buyer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.galaxy_techno.buyer.model.entity.User

@Database(
    entities = [User::class],
    version = 2,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getDao(): UserDao
}