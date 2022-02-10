package com.imperium.weatherapplication.Retrofit

import com.imperium.weatherapplication.Model.WeatherModel
import retrofit2.http.GET

interface WeatherRetrofit {

    @GET("onecall")
    suspend fun get(): WeatherModel
}