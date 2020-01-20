package com.aitor.samplemarket

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
}