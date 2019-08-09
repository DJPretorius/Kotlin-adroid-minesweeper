package com.example.kotlintest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatusViewModel : ViewModel() {
    val stateLD : MutableLiveData<Boolean>by lazy {
        MutableLiveData<Boolean>()
    }
}