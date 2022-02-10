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
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.UI.ViewModels.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@SuppressLint("CustomSplashScreen")
@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashScreen : BaseFragment<FragmentSplashScreenBinding>() {

    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity?)!!.supportActionBar!!.hide()
    }

    override fun observeData() {
        super.observeData()
        observeSplashLiveData(binding.root)
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

    override fun getViewBinding(): FragmentSplashScreenBinding = FragmentSplashScreenBinding.inflate(layoutInflater)
}