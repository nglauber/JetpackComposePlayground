package br.com.nglauber.jetpackcomposeplayground.rest2

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.nglauber.jetpackcomposeplayground.rest.model.RequestState
import br.com.nglauber.jetpackcomposeplayground.rest2.model.Doggy
import br.com.nglauber.jetpackcomposeplayground.rest2.model.DoggyHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DogsViewModel : ViewModel(), DefaultLifecycleObserver {
    private var loadDogsJob: Job? = null

    private val _state = MutableStateFlow<RequestState<List<Doggy>>>(
        RequestState.Idle
    )
    val dogsList: StateFlow<RequestState<List<Doggy>>> get() = _state


    init {
        loadDogs()
    }

    fun loadDogs() {
        loadDogsJob?.cancel()
        loadDogsJob = viewModelScope.launch {
            _state.value = RequestState.Loading
            try {
                val dogs = withContext(Dispatchers.IO) {
                    DoggyHttp.loadDogsGson()
                }
                _state.value = RequestState.Success(dogs)
            } catch (t: Throwable) {
                t.printStackTrace()
                _state.value = RequestState.Error(t)
            }
        }
    }
}