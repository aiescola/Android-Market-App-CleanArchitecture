package com.aitor.samplemarket.shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.aitor.samplemarket.R
import com.aitor.samplemarket.base.features.BottomMenuFeature
import com.aitor.samplemarket.databinding.ActivityShopBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Simple activity with a bottom navigation view and two fragments (one for the product list,
 * another one for the cart).
 *
 * Navigation through fragments is implemented through Jetpack's Navigation module.
 */
@AndroidEntryPoint
class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding

    @Inject lateinit var bottomMenuFeature: BottomMenuFeature

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val navController: NavController = findNavController(R.id.navigation_controller)
        navController.setGraph(bottomMenuFeature.navigationResId)
        NavigationUI.setupWithNavController(binding.navView, navController)

        val navMenu = binding.navView
        navMenu.inflateMenu(bottomMenuFeature.menuResId)

    }
}
