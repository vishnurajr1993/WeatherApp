package com.imperium.weatherapplication.Repository

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import com.imperium.weatherapplication.Retrofit.WeatherRetrofit
import com.imperium.weatherapplication.Room.UserDao
import com.imperium.weatherapplication.Room.UserEntity
import com.imperium.weatherapplication.Utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MainRepository
@Inject constructor(
    private val userDao: UserDao,
    private val weatherRetrofit: WeatherRetrofit,

){
    fun getAllUsers():LiveData<List<UserEntity>>{
        return userDao.getAllUsers()
    }

    suspend fun login(userName:String,password:String  ): Flow<DataState<Boolean>> = flow {
        Log.d("llllls", "login: ")
        emit(DataState.Loading)
        delay(1000)// mimic network delay for showing loader

        try{
            if(userName.isEmpty() && !isValidEmail(userName)){
                emit(DataState.Error(userNameValidationError))
            }else if(password.isEmpty() && !isValidPassword(password)){
                emit(DataState.Error(passwordValidationError))
            }else{
                emit(DataState.Success(data = true))
            }
        }catch (e: Exception){
            emit(DataState.Error(e.message.toString()))
        }
    }

    suspend fun addUser(entity: UserEntity){
        userDao.addUser(entity)
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidPassword(password: String?) : Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        } ?: return false
    }
}


