package com.aitor.samplemarket.shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.aitor.samplemarket.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Simple activity with a bottom navigation view and two fragments (one for the product list,
 * another one for the cart).
 *
 * Navigation through fragments is implemented through Jetpack's Navigation module.
 */
class ShopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.navigation_controller)

        NavigationUI.setupWithNavController(navView, navController)
    }
}
