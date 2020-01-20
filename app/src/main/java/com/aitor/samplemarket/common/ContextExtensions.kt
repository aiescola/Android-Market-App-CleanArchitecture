package com.aitor.samplemarket.common

import android.content.Context
import android.widget.Toast
import com.aitor.samplemarket.R

fun Context.showToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.getPriceString(price: Double) = getString(R.string.price_format, price) ?: ""
