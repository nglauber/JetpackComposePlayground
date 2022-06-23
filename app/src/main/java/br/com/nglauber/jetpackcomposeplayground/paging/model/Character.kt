package br.com.nglauber.jetpackcomposeplayground.paging.model

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)