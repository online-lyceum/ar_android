package com.exception.ufofind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vuforia.engine.native_sample.gson.UserJson
import com.vuforia.engine.native_sample.retrofit.RetrofitManager

class AllUsersViewModel(application: Application) : AndroidViewModel(application) {
    val liveDataUsers = MutableLiveData<List<UserJson>>()
    init {
        RetrofitManager.getUsers {
            liveDataUsers.value = it?.users
        }
    }

    class AllUsersViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllUsersViewModel(application) as T
        }
    }
}