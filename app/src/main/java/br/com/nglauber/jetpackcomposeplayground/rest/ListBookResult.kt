package br.com.nglauber.jetpackcomposeplayground.rest

import br.com.nglauber.jetpackcomposeplayground.rest.model.Book

data class ListBookResult(
    var loading: Boolean,
    var books: List<Book>
)