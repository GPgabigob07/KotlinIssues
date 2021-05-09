package com.nta.githubkotlinissueapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.nta.githubkotlinissueapp.R
import com.nta.githubkotlinissueapp.databinding.ActivityMainBinding
import com.nta.githubkotlinissueapp.utils.lazyBind

class MainActivity : AppCompatActivity() {

    private val binder: ActivityMainBinding by lazyBind(R.layout.activity_main)

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binder) {

            setSupportActionBar(toolbar)

            val host =
                supportFragmentManager.findFragmentById(binder.navHostFragment.id) as? NavHostFragment
            host?.apply {
                this@MainActivity.navController = navController
            }
            setupActionBarWithNavController(navController)

            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }
}