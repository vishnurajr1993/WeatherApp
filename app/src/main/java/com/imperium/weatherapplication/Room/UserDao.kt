package com.imperium.weatherapplication.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(blogEntity: UserEntity): Long

    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<UserEntity>>
}