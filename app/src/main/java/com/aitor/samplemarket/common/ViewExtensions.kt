package com.aitor.samplemarket.common

import android.widget.EditText

fun EditText.toInt(): Int = text.toString().toIntOrNull() ?: 0