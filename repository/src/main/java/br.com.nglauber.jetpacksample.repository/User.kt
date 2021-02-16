package br.com.nglauber.jetpacksample.repository

data class User(
    val uid: Long,
    var name: String,
    var isActive: Boolean,
    var socialNetwork: String
)