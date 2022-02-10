package com.imperium.weatherapplication.Room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [UserEntity::class ], version = 1)
abstract class UserDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        val DATABASE_NAME: String = "user_db"
    }


}