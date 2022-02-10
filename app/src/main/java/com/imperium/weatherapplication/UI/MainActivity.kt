package com.imperium.weatherapplication.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var navController: NavController? = null
    @Inject
    lateinit var  main: MainRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        navController = Navigation.findNavController(this, R.id.main);
        NavigationUI.setupActionBarWithNavController(this, navController!!)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp()
    }
}