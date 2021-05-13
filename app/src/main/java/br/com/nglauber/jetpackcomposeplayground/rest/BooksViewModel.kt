package br.com.nglauber.jetpackcomposeplayground.rest

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

class BooksViewModel : ViewModel() {
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
}