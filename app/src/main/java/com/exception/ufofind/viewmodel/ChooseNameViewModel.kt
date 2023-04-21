package com.exception.ufofind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChooseNameViewModel(application: Application) : AndroidViewModel(application) {
    var permissionsChecked = false

    class ChooseNameViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChooseNameViewModel(application) as T
        }
    }
}