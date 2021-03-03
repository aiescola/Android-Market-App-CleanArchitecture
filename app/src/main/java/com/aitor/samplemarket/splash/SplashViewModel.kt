package com.aitor.samplemarket.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias SplashStatusLoading = SplashViewModel.Status.Loading
typealias SplashStatusLoaded = SplashViewModel.Status.Loaded

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {

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