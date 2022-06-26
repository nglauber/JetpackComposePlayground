package br.com.nglauber.jetpackcomposeplayground.paging.model

data class Thumbnail(
    val path: String,
    val extension: String
) {
    val pathSec: String
        get() = path.replace("http:", "https:")
}