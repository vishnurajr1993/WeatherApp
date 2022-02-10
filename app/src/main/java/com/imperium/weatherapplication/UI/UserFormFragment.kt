package com.imperium.weatherapplication.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.imperium.weatherapplication.MainActivity
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Utils.DataState
import com.imperium.weatherapplication.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentUserFormBinding
import com.imperium.weatherapplication.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserFormFragment : Fragment() {
    private val vm: WeatherAppViewModel by viewModels()
    lateinit var bindingUserFormFragment: FragmentUserFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity?)!!.supportActionBar!!.title = getString(R.string.app_name)
        getActivity()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        bindingUserFormFragment=FragmentUserFormBinding.inflate(inflater,container,false)
        observeUserData()
        bindingUserFormFragment.save.setOnClickListener {
            addUser()
        }
        bindingUserFormFragment.cancel.setOnClickListener {
                popBackStack()
        }
        return bindingUserFormFragment.root
    }


    private fun addUser(){
        val firstName:String=bindingUserFormFragment.formFirstName.text.trim().toString()
        val lastName=bindingUserFormFragment.formLastName.text.trim().toString()
        val email=bindingUserFormFragment.formEmail.text.trim().toString()
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
}