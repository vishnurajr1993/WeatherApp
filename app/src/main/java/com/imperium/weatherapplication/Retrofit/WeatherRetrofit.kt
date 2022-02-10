package com.imperium.weatherapplication.Retrofit

import com.imperium.weatherapplication.Model.WeatherModel
import retrofit2.Response

import retrofit2.http.*

interface WeatherRetrofit {
    @Headers("Accept: application/json")
    @GET("onecall")
    suspend fun getWeather(@Query("lat", encoded = true) latitude: String,
                           @Query("lon", encoded = true) longitude: String,
                           @Query("units", encoded = true) units: String,
                           @Query("appid", encoded = true) appId: String,
                           ): Response<WeatherModel>
}