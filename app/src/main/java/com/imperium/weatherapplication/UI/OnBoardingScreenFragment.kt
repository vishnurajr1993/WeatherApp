package com.imperium.weatherapplication.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.imperium.weatherapplication.MainActivity
import com.imperium.weatherapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingScreenFragment : Fragment() {

    lateinit var loginButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity?)!!.supportActionBar!!.hide()
        val view=inflater.inflate(R.layout.fragment_onboarding_screen, container, false)
        loginButton=view.findViewById(R.id.login_btn_on_boarding)
        loginButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_onBoardingScreenFragment_to_logInFragment);
        }
        return  view
    }


}