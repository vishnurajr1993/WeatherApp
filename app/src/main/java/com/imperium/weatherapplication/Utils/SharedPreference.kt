package com.imperium.weatherapplication.Utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject



class SharedPreference @Inject constructor(private val preferences: SharedPreferences){
    fun getIsLoggedIn() = preferences.getBoolean(IS_LOGGED_IN, false)

    fun setIsLoggedIn(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_LOGGED_IN, value)
        editor.apply()
    }
    fun getIsOnBoardingCompleted() = preferences.getBoolean(IS_ON_BOARDING_COMPLETED, false)

    fun setIsOnBoardingComplete(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_ON_BOARDING_COMPLETED, value)
        editor.apply()
    }

}