package com.imperium.weatherapplication

import android.util.Log
import android.util.Patterns
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.imperium.weatherapplication.Model.weatherModelToWeatherEntity
import com.imperium.weatherapplication.Repository.MainRepository
import com.imperium.weatherapplication.Retrofit.WeatherEntity
import com.imperium.weatherapplication.Room.UserEntity
import com.imperium.weatherapplication.Utils.DataState
import com.imperium.weatherapplication.Utils.EMAIL_REJEX
import com.imperium.weatherapplication.Utils.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Error
import java.lang.Exception

@ExperimentalCoroutinesApi
public class WeatherAppViewModel @ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
):ViewModel() {


    private val _loginState = MutableSharedFlow<DataState<Boolean>>()
    val loginState: SharedFlow<DataState<Boolean>>
        get() = _loginState.asSharedFlow()


    private val _userList:MutableLiveData<List<UserEntity>> = MutableLiveData()
    val userList:LiveData<List<UserEntity>>
    get()=_userList


    private  val _splashTimer: MutableLiveData<Boolean> = MutableLiveData()
    val splashTimer:LiveData<Boolean>
    get()=_splashTimer

    private val _createUser:MutableLiveData<DataState<Long>> = MutableLiveData()
    val createUser:LiveData<DataState<Long>>
        get()=_createUser

    init {
        viewModelScope.launch {
            //mainRepository.addUser(UserEntity(id = 0, firstName = "vishnu", lastName = "raj", email = "a@gmail.com"));
            //mainRepository.addUser(UserEntity(id = 0, firstName = "arun", lastName = "a", email = "b@gmail.com"));
            mainRepository.getAllUsers().observeForever {
             _userList.value=it

            }
        }
    }



    fun login(userName:String,password:String){
        viewModelScope.launch {
            //mainRepository.login("d","d")
            mainRepository.login(userName,password).onEach {
                _loginState.emit(it)
            }.launchIn(viewModelScope)
        }
    }

     fun deleteUser(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteUser(id)
        }
    }

    fun createUser(firstName:String,lastName:String,email:String){
        viewModelScope.launch() {
            _createUser.value=DataState.Loading
            if(firstName.isEmpty()){
                _createUser.value=DataState.Error(exception = "Invalid FirstName")
            }else if(lastName.isEmpty()){
                _createUser.value=DataState.Error(exception = "Invalid LastName")
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                _createUser.value=DataState.Error(exception = "Invalid email")
            }else{
                val entity=UserEntity(id = 0,firstName=firstName, lastName = lastName, email = email)
                mainRepository.addUser(entity)
                _createUser.value=DataState.Success(data = 1)
            }

        }
    }



    fun initSplashScreen() {
        viewModelScope.launch {
            delay(2000)
            updateSplashTimer()
        }
    }

    private fun updateSplashTimer() {
        _splashTimer.value = true
    }


    fun isUserLoggedIn():Boolean=mainRepository.isUserLoggedIn()

    fun isOnBoardingCompleted():Boolean=mainRepository.isOnBoardingCompleted()

    fun setIsOnBoardingCompleted(){
        mainRepository.setIsOnBoardingCompleted()
    }


    fun logOut(){
        mainRepository.setUserLoggedIn(false)
    }

    fun getWeather():LiveData<DataState<WeatherEntity>>{
        return liveData(Dispatchers.IO) {
            emit(DataState.Loading)
            try {
            val response=mainRepository.getWeather(lattitude = "12.9082847623315",
            longitude = "77.65197822993314", units = "imperial", apiId = BuildConfig.SecAPIKEY)

                if(response.isSuccessful){
                    val entity=response.body()?.weatherModelToWeatherEntity()
                    entity?.let {

                        emit(DataState.Success(data = entity))
                    }
                    Log.d("resp", response.body().toString()+response.code())
                }else{
                    emit(DataState.Error(exception = response.errorBody().toString()))
                }

            }catch (e:Exception){
                emit(DataState.Error(exception = e.message.toString()))
            }
        }
    }

}
