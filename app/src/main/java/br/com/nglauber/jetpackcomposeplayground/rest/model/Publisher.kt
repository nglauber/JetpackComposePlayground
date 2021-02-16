package br.com.nglauber.jetpackcomposeplayground.rest.model

import com.google.gson.annotations.SerializedName

data class Publisher (
    @SerializedName("novatec")
    var categories: List<Category> = emptyList()
)