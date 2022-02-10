package com.imperium.weatherapplication.UI

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.imperium.weatherapplication.MainActivity
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Utils.DataState
import com.imperium.weatherapplication.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentUserListBinding
import com.imperium.weatherapplication.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private val vm: WeatherAppViewModel by viewModels()
    private lateinit var bindingWeatherFragment:FragmentWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_menu,menu)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity?)!!.supportActionBar!!.show()
        (activity as MainActivity?)!!.supportActionBar!!.title = getString(R.string.weather_fragment)
        (activity as MainActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        bindingWeatherFragment= FragmentWeatherBinding.inflate(inflater,container,false)
        setUpObserver()
        return bindingWeatherFragment.root
    }

    private fun setUpObserver() {
        vm.getWeather().observe(this, Observer { state->
            state?.let {resource->
                when(resource){
                    is DataState.Success->{
                        hideProgressBar()
                        print(resource.data.humidity.toString() +"isHumid")
                        bindingWeatherFragment.temperature.text=resource.data.temperature.toString()+" °F"
                        bindingWeatherFragment.humidity.text=resource.data.humidity.toString()+" %"
                        bindingWeatherFragment.windSpeed.text=resource.data.windSpeed.toString()+" mph"
                        bindingWeatherFragment.type.text=resource.data.weatherType.toString()
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
        bindingWeatherFragment.type.visibility=View.GONE
        bindingWeatherFragment.container.visibility=View.GONE
        bindingWeatherFragment.progressBar.visibility=View.VISIBLE
    }
    fun hideProgressBar(){
        bindingWeatherFragment.type.visibility=View.VISIBLE
        bindingWeatherFragment.container.visibility=View.VISIBLE
        bindingWeatherFragment.progressBar.visibility=View.GONE
    }

}