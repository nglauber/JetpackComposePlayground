package br.com.nglauber.jetpackcomposeplayground.rest.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categoria")
    var name: String = "",
    @SerializedName("livros")
    var books: List<Book> = emptyList()
)