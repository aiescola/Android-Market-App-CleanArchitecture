package com.aitor.samplemarket.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aitor.samplemarket.shop.ShopActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    private val statusObserver = Observer<SplashViewModel.Status> {
        when (it) {
            is SplashStatusLoaded -> {
                Timber.d("Finished!")
                navigateToShop()
            }
            is SplashStatusLoading -> {
                // Do nothing in the demo.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        splashViewModel.splashStatus.observe(this, statusObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        splashViewModel.splashStatus.removeObserver(statusObserver)
    }

    private fun navigateToShop() {
        startActivity(Intent(this, ShopActivity::class.java))
        finish()
    }
}
