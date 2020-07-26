package com.aitor.samplemarket.shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.aitor.samplemarket.R
import com.aitor.samplemarket.databinding.ActivityShopBinding

/**
 * Simple activity with a bottom navigation view and two fragments (one for the product list,
 * another one for the cart).
 *
 * Navigation through fragments is implemented through Jetpack's Navigation module.
 */
class ShopActivity : AppCompatActivity() {


    private lateinit var binding: ActivityShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navController = findNavController(R.id.navigation_controller)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }
}
