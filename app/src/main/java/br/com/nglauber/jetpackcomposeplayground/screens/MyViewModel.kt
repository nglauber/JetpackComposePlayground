package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyViewModel : ViewModel() {
    private val _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    private val _listFlow = MutableStateFlow<List<String>>(emptyList())
    val listFlow = _listFlow.asStateFlow()

    fun inc() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun loadList() {
        _listFlow.value = (0..5).map { "Item $it" }.toList()
    }
}