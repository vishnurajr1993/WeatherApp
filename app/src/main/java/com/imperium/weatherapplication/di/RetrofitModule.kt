package com.imperium.weatherapplication.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.imperium.weatherapplication.BuildConfig
import com.imperium.weatherapplication.Retrofit.WeatherRetrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson:  Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideWeatherService(retrofit: Retrofit.Builder): WeatherRetrofit {
        return retrofit
            .build()
            .create(WeatherRetrofit::class.java)
    }

}