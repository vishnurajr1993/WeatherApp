package com.imperium.weatherapplication.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Utils.DataState
import com.imperium.weatherapplication.UI.ViewModels.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentLogInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>() {
    override fun setUpViews() {

        binding.btnLogin.setOnClickListener {
            vm.login(binding.etEmail.text.trim().toString(),
                binding.etPassword.text.trim().toString())
        }
    }

    override fun setUpAppbar() {
        super.setUpAppbar()
        (activity as MainActivity?)!!.supportActionBar!!.hide()
    }

    override fun observeData() {
        super.observeData()
        subscribeObservers(binding.root)
    }
    private fun subscribeObservers(view: View) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted{
            vm.loginState.collectLatest { dataState ->
                when(dataState){
                    is DataState.Success<Boolean> -> {
                        hideProgressbar()
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

    }

    private fun showProgressbar(){
        binding.apply {

            progressBar.visibility=View.VISIBLE
            btnLogin.visibility=View.GONE
        }
    }

    private fun hideProgressbar(){
        binding.apply {

            progressBar.visibility=View.GONE
            btnLogin.visibility=View.VISIBLE
        }
    }

    override fun getViewBinding(): FragmentLogInBinding = FragmentLogInBinding.inflate(layoutInflater)

}
