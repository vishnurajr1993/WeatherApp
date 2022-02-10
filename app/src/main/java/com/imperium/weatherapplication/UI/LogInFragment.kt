package com.imperium.weatherapplication.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.imperium.weatherapplication.MainActivity
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Utils.DataState
import com.imperium.weatherapplication.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentLogInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LogInFragment : Fragment() {

    private val vm: WeatherAppViewModel by viewModels()
    private lateinit var bindingLogin: FragmentLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity?)!!.supportActionBar!!.hide()
        getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        bindingLogin=FragmentLogInBinding.inflate(inflater,container,false)
        subscribeObservers(bindingLogin.root)
        bindingLogin.btnLogin.setOnClickListener {

                vm.login(bindingLogin.etEmail.text.trim().toString(),
                    bindingLogin.etPassword.text.trim().toString())


        }
        return bindingLogin.root

    }

    private fun subscribeObservers(view: View) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted{
            vm.loginState.collectLatest { dataState ->
                when(dataState){
                    is DataState.Success<Boolean> -> {
                        hideProgressbar()
                        //Toast.makeText( activity,"login success", Toast.LENGTH_SHORT).show()
//                    displayProgressBar(false)
//                    appendBlogTitles(dataState.data)
                        GlobalScope.launch(Dispatchers.Main) {

                            Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_userListFragment);
                        }
                    }
                    is DataState.Error -> {
                        hideProgressbar()
                        Toast.makeText( activity,dataState.exception, Toast.LENGTH_SHORT).show()
                    }
                    is DataState.Loading -> {
                        showProgressbar()
                    }
                }
            }
        }
        /*vm.loginState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    Toast.makeText( activity,"login success", Toast.LENGTH_SHORT).show()
//                    displayProgressBar(false)
//                    appendBlogTitles(dataState.data)
                    GlobalScope.launch(Dispatchers.Main) {

                        Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_userListFragment);
                    }
                }
                is DataState.Error -> {
                    Toast.makeText( activity,"login error", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    Toast.makeText( activity,"login loading", Toast.LENGTH_SHORT).show()
                }
            }
        })*/
    }

    fun showProgressbar(){
        bindingLogin.progressBar.visibility=View.VISIBLE
        bindingLogin.btnLogin.visibility=View.GONE
    }

    fun hideProgressbar(){
        bindingLogin.progressBar.visibility=View.GONE
        bindingLogin.btnLogin.visibility=View.VISIBLE
    }

}
