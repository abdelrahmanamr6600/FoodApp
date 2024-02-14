package com.abdelrahman.foodapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.abdelrahman.foodapp.R
import com.abdelrahman.foodapp.data.db.MealDataBase
import com.abdelrahman.foodapp.databinding.ActivityMainBinding
import com.abdelrahman.foodapp.mvvm.HomeViewModel
import com.abdelrahman.foodapp.mvvm.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityMainBinding
   val viewModel:HomeViewModel by lazy {
       val mealDataBase = MealDataBase.getInstance(this)
     val homeViewModelProvider = HomeViewModelFactory(mealDataBase)
     ViewModelProvider(this,homeViewModelProvider)[HomeViewModel::class.java]
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.btn_nav)
            .setupWithNavController(navController)
    }
}