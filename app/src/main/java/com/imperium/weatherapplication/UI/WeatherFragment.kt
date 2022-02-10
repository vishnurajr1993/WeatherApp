package com.imperium.weatherapplication.UI

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Utils.DataState
import com.imperium.weatherapplication.UI.ViewModels.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {



    override fun setUpAppbar() {
        super.setUpAppbar()
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_menu,menu)
    }

    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity?)!!.supportActionBar!!.show()
        (activity as MainActivity?)!!.supportActionBar!!.title = getString(R.string.weather_fragment)
        (activity as MainActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun observeData() {
        super.observeData()
        setUpObserver()
    }
    private fun setUpObserver() {
        vm.getWeather().observe(this, Observer { state->
            state?.let {resource->
                when(resource){
                    is DataState.Success->{
                        hideProgressBar()
                        print(resource.data.humidity.toString() +"isHumid")
                        binding.apply {

                            temperature.text=resource.data.temperature.toString()+" °F"
                            humidity.text=resource.data.humidity.toString()+" %"
                            windSpeed.text=resource.data.windSpeed.toString()+" mph"
                            type.text=resource.data.weatherType.toString()
                        }
                    }
                    is DataState.Error->{
                        showProgressBar()
                        print(resource.exception +"isHumid")
                    }
                    is DataState.Loading->{
                        showProgressBar()
                        print("loading")
                    }
                }
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.log_out->{
                vm.logOut()
                findNavController().navigate(R.id.action_weatherFragment_to_logInFragment)
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
    fun showProgressBar(){
        binding.apply {
            type.visibility=View.GONE
            container.visibility=View.GONE
            progressBar.visibility=View.VISIBLE
        }

    }
    fun hideProgressBar(){
        binding.apply {
            type.visibility=View.VISIBLE
            container.visibility=View.VISIBLE
            progressBar.visibility=View.GONE
        }

    }

    override fun getViewBinding(): FragmentWeatherBinding = FragmentWeatherBinding.inflate(layoutInflater)

}