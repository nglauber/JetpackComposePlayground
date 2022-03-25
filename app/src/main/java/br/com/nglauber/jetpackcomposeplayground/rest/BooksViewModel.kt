package br.com.nglauber.jetpackcomposeplayground.rest

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.nglauber.jetpackcomposeplayground.rest.model.Book
import br.com.nglauber.jetpackcomposeplayground.rest.model.BookHttp
import br.com.nglauber.jetpackcomposeplayground.rest.model.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BooksViewModel : ViewModel(), DefaultLifecycleObserver {
    private val _state = MutableStateFlow<RequestState<List<Book>>>(
        RequestState.Idle
    )
    val booksList: StateFlow<RequestState<List<Book>>> get() = _state

    fun loadBooks() {
        viewModelScope.launch {
            _state.value = RequestState.Loading
            try {
                val books = withContext(Dispatchers.IO) {
                    BookHttp.loadBooksGson()
                }
                _state.value = RequestState.Success(books)
            } catch (t: Throwable) {
                t.printStackTrace()
                _state.value = RequestState.Error(t)
            }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d("NGVL", "onPause")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d("NGVL", "onResume")
    }
}