package com.example.kotlintest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatusViewModel : ViewModel() {
    val gameOver : MutableLiveData<Boolean>by lazy {
        MutableLiveData<Boolean>()
    }
    val statusChanged : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}