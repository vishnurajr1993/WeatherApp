package com.imperium.weatherapplication.Repository

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import com.imperium.weatherapplication.Model.WeatherModel
import com.imperium.weatherapplication.Retrofit.WeatherRetrofit
import com.imperium.weatherapplication.Room.UserDao
import com.imperium.weatherapplication.Room.UserEntity
import com.imperium.weatherapplication.Utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class MainRepository
@Inject constructor(
    private val userDao: UserDao,
    private val weatherRetrofit: WeatherRetrofit,
    private val sharedPref: SharedPreference,

){
    fun getAllUsers():LiveData<List<UserEntity>>{
        return userDao.getAllUsers()
    }

    suspend fun login(userName:String,password:String  ): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try{
            if(userName.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userName).matches()){
                emit(DataState.Error(userNameValidationError))
            }else if(password.isEmpty() || !password.matches(PASSWORD_PATTERN.toRegex())){
                emit(DataState.Error(passwordValidationError))
            }else if(userName.equals("testapp@google.com") &&
                password.equals("Test@123456")){
                delay(1000)// mimic network delay for showing loader
                setUserLoggedIn(true)
                emit(DataState.Success(data = true))

            }else{
                emit(DataState.Error(invalidCredentials))
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message.toString()))
        }
    }

    suspend fun addUser(entity: UserEntity):Long{
        return userDao.addUser(entity)
    }

    suspend fun deleteUser(id:Int){
        userDao.deleteByUserId(id)
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun isUserLoggedIn():Boolean=sharedPref.getIsLoggedIn()

    fun isOnBoardingCompleted():Boolean=sharedPref.getIsOnBoardingCompleted()

    fun setIsOnBoardingCompleted(){
        sharedPref.setIsOnBoardingComplete(true)
    }

    fun setUserLoggedIn(isLoggedIn:Boolean){
        sharedPref.setIsLoggedIn(isLoggedIn)
    }

    suspend fun getWeather(lattitude:String,
                           longitude:String,
                           units:String,
                           apiId:String):Response<WeatherModel>{
        return weatherRetrofit
            .getWeather(latitude = lattitude,
                longitude = longitude,
                units = units, appId = apiId)
    }


}


