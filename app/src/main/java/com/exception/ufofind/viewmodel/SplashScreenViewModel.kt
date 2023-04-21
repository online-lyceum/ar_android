package com.exception.ufofind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {
    val liveDataCanContinue = MutableLiveData(false)

    init {
        CoroutineScope(Dispatchers.Main).launch {
            val deferred = CoroutineScope(Dispatchers.Default).async {
                Thread.sleep(4000L)
                return@async true
            }
            liveDataCanContinue.value = deferred.await()
        }
    }


    class SplashScreenViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SplashScreenViewModel(application) as T
        }
    }
}