package com.aitor.samplemarket.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

fun ViewGroup.inflate(resId: Int): View {
    return LayoutInflater.from(this.context).inflate(resId, this, false)
}

fun EditText.toInt(): Int = text.toString().toInt()