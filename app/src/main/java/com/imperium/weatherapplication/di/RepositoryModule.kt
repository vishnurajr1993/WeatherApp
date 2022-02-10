/*
package com.imperium.weatherapplication.di

import com.imperium.weatherapplication.Repository.MainRepository
import com.imperium.weatherapplication.Retrofit.WeatherRetrofit
import com.imperium.weatherapplication.Room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        userDao: UserDao,
        retrofit: WeatherRetrofit,

    ): MainRepository{
        return MainRepository(userDao = userDao, weatherRetrofit = retrofit)
    }
}*/
