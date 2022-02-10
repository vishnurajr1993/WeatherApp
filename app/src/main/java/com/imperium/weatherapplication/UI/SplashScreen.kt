package com.imperium.weatherapplication.UI

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.imperium.weatherapplication.MainActivity
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.WeatherAppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@SuppressLint("CustomSplashScreen")
@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashScreen : Fragment() {
    private val vm: WeatherAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity?)!!.supportActionBar!!.hide()
        val view:View=inflater.inflate(R.layout.fragment_splash_screen, container, false)

        observeSplashLiveData(view)
        return view
    }


    private fun observeSplashLiveData(view: View) {
        vm.initSplashScreen()
        val observer = Observer<Boolean> {
            val isLoggedIn=vm.isUserLoggedIn()
            val isOnBoardingCompleted=vm.isOnBoardingCompleted()

            if(isLoggedIn){
                findNavController(this).navigate(R.id.action_splashScreen_to_userListFragment)
            }else if(isOnBoardingCompleted){
                findNavController(this).navigate(R.id.splash_to_login);
            }else {
                Navigation.findNavController(view)
                    .navigate(R.id.action_splashScreen_to_onBoardingScreenFragment);
            }

        }
        vm.splashTimer.observe(this, observer)
    }

}