package com.imperium.weatherapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.imperium.weatherapplication.Retrofit.WeatherEntity

data class WeatherModel(
    @SerializedName("current")
    @Expose
    val current: Current,
    @SerializedName("daily")
    @Expose
    val daily: List<Daily>,
    @SerializedName("hourly")
    @Expose
    val hourly: List<Hourly>,
    @SerializedName("lat")
    @Expose
    val lat: Double,
    @SerializedName("lon")
    @Expose
    val lon: Double,
    @SerializedName("minutely")
    @Expose
    val minutely: List<Minutely>,
    @SerializedName("timezone")
    @Expose
    val timezone: String,
    @SerializedName("timezone_offset")
    @Expose
    val timezone_offset: Int
) {
    data class Current(
        @SerializedName("clouds")
        @Expose
        val clouds: Int,
        @SerializedName("dew_point")
        @Expose
        val dew_point: Double,
        @SerializedName("dt")
        @Expose
        val dt: Int,
        @SerializedName("feels_like")
        @Expose
        val feels_like: Double,
        @SerializedName("humidity")
        @Expose
        val humidity: Int,
        @SerializedName("pressure")
        @Expose
        val pressure: Int,
        @SerializedName("sunrise")
        @Expose
        val sunrise: Int,
        @SerializedName("sunset")
        @Expose
        val sunset: Int,
        @SerializedName("temp")
        @Expose
        val temp: Double,
        @SerializedName("uvi")
        @Expose
        val uvi: Double,
        @SerializedName("visibility")
        @Expose
        val visibility: Int,
        @SerializedName("weather")
        @Expose
        val weather: List<Weather>,
        @SerializedName("wind_deg")
        @Expose
        val wind_deg: Int,
        @SerializedName("wind_gust")
        @Expose
        val wind_gust: Double,
        @SerializedName("wind_speed")
        @Expose
        val wind_speed: Double
    ) {
        data class Weather(
            @SerializedName("description")
            @Expose
            val description: String,
            @SerializedName("icon")
            @Expose
            val icon: String,
            @SerializedName("id")
            @Expose
            val id: Int,
            @SerializedName("main")
            @Expose
            val main: String
        )
    }

    data class Daily(

        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val feels_like: FeelsLike,
        val humidity: Int,
        val moon_phase: Double,
        val moonrise: Int,
        val moonset: Int,
        val pop: Int,
        val pressure: Int,
        val snow: Double,
        val sunrise: Int,
        val sunset: Int,
        val temp: Temp,
        val uvi: Double,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_gust: Double,
        val wind_speed: Double
    ) {
        data class FeelsLike(
            val day: Double,
            val eve: Double,
            val morn: Double,
            val night: Double
        )

        data class Temp(
            val day: Double,
            val eve: Double,
            val max: Double,
            val min: Double,
            val morn: Double,
            val night: Double
        )

        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }

    data class Hourly(
        @SerializedName("clouds")
        @Expose
        val clouds: Int,
        @SerializedName("dew_point")
        @Expose
        val dew_point: Double,
        @SerializedName("dt")
        @Expose
        val dt: Int,
        @SerializedName("feels_like")
        @Expose
        val feels_like: Double,
        val humidity: Int,
        val pop: Int,
        val pressure: Int,
        val temp: Double,
        val uvi: Double,
        val visibility: Int,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_gust: Double,
        val wind_speed: Double
    ) {
        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }

    data class Minutely(
        val dt: Int,
        val precipitation: Int
    )
}

fun WeatherModel.weatherModelToWeatherEntity():WeatherEntity{
    return WeatherEntity(temperature = this.current.temp,
        weatherType = this.current.weather[0].main,
        humidity = this.current.humidity,
        windSpeed = this.current.wind_speed)
}