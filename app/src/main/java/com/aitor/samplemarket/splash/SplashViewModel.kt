package com.aitor.samplemarket.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

typealias SplashStatusLoading = SplashViewModel.Status.Loading
typealias SplashStatusLoaded = SplashViewModel.Status.Loaded

class SplashViewModel : ViewModel() {

    sealed class Status {
        object Loading : Status()
        object Loaded : Status()
    }

    private val _splashStatus = MutableLiveData<Status>()

    val splashStatus: LiveData<Status>
        get() = _splashStatus

    init {
        viewModelScope.launch {
            _splashStatus.postValue(Status.Loading)
            delay(1500)
            _splashStatus.postValue(Status.Loaded)
        }
    }
}