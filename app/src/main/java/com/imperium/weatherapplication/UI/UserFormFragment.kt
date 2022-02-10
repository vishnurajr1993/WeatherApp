package com.imperium.weatherapplication.UI


import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Utils.DataState
import com.imperium.weatherapplication.UI.ViewModels.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentUserFormBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserFormFragment : BaseFragment<FragmentUserFormBinding>() {

    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity?)!!.supportActionBar!!.title = getString(R.string.app_name)
        getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        binding.save.setOnClickListener {
            addUser()
        }
        binding.cancel.setOnClickListener {
            popBackStack()
        }
    }

    override fun observeData() {
        super.observeData()
        observeUserData()
    }

    private fun addUser(){
        val firstName:String=binding.formFirstName.text.trim().toString()
        val lastName=binding.formLastName.text.trim().toString()
        val email=binding.formEmail.text.trim().toString()
        vm.createUser(firstName = firstName, lastName = lastName, email = email)
    }

    private fun observeUserData() {
        vm.createUser.observe(viewLifecycleOwner, Observer {state->
            when(state){
               is DataState.Success->{
                    popBackStack()
                }
                is DataState.Error->{
                    Toast.makeText(activity, state.exception, Toast.LENGTH_SHORT).show()
                }is DataState.Loading->{
                    // can show progress indicator
                }
            }

        })
    }
    private fun popBackStack(){
        findNavController().popBackStack()
    }

    override fun getViewBinding(): FragmentUserFormBinding = FragmentUserFormBinding.inflate(layoutInflater)
}