package com.imperium.weatherapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.imperium.weatherapplication.Repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var navController: NavController? = null
    @Inject
    lateinit var  main: MainRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this,R.id.main);
        NavigationUI.setupActionBarWithNavController(this, navController!!)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp()
    }
}