package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    fun inc() {
        _count.value = (_count.value ?: 0) + 1
    }
}