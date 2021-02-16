package br.com.nglauber.jetpackcomposeplayground.rest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.nglauber.jetpackcomposeplayground.rest.model.BookHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BooksViewModel: ViewModel() {
    val booksResult =
        MutableLiveData<ListBookResult>().apply {
            value = ListBookResult(false, emptyList())
        }

    fun loadBooks() {
        viewModelScope.launch {
            booksResult.value = booksResult.value?.copy(loading = true)
            val books = withContext(Dispatchers.IO) {
                BookHttp.loadBooksGson()
            }
            booksResult.value = booksResult.value?.copy(
                books = books ?: emptyList(),
                loading = false
            )
        }
    }
}