package com.imperium.weatherapplication

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.imperium.weatherapplication.Repository.MainRepository
import com.imperium.weatherapplication.Room.UserEntity
import com.imperium.weatherapplication.Utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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


    init {
        viewModelScope.launch {
            mainRepository.addUser(UserEntity(id = 0, firstName = "vishnu", lastName = "raj", email = "a@gmail.com"));
            mainRepository.getAllUsers().observeForever {
             _userList.value=it
                for(i in it){

                    Log.d("TAG>>", "use:${i.firstName} ")
                }
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

}
