package com.exception.ufofind.viewmodel

import android.app.Application
import androidx.lifecycle.*

class GuideViewModel(application: Application) : AndroidViewModel(application) {
    val liveDataPageCount = MutableLiveData<Int>(1)
    val liveDataShowApplyButton = MutableLiveData<Boolean>(false)

    fun nextPage() {
        var page = liveDataPageCount.value!!
        page++
        liveDataPageCount.value = page
        if(page== AMOUNT_PAGES) {
            liveDataShowApplyButton.value = true
        }
    }

    fun previousPage() {
        var page = liveDataPageCount.value!!
        page--
        liveDataPageCount.value = page
    }

    class GuideViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GuideViewModel(application) as T
        }
    }

    companion object{
        const val AMOUNT_PAGES = 4
    }
}