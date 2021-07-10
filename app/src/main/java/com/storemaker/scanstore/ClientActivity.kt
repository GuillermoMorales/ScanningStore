package com.storemaker.scanstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_client.*
import kotlinx.android.synthetic.main.activity_home.*

class ClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.clientNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        client_bottom_nav.setupWithNavController(navController)
        setUpActionBar(navController)


    }

    //Navigation method
    private fun setUpActionBar(navController: NavController){
        NavigationUI.setupActionBarWithNavController(this, navController, client_drawer_layout)
    }
}