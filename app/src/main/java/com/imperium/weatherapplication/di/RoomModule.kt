package com.imperium.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.imperium.weatherapplication.Room.UserDao
import com.imperium.weatherapplication.Room.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideUserDb(@ApplicationContext context: Context): UserDataBase {
        return Room
            .databaseBuilder(
                context,
                UserDataBase::class.java,
                UserDataBase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDAO(userDatabase: UserDataBase): UserDao {
        return userDatabase.userDao()
    }
}