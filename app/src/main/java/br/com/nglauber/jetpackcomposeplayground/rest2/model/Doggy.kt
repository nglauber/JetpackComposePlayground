package br.com.nglauber.jetpackcomposeplayground.rest2.model

import com.google.gson.annotations.SerializedName

data class Doggy(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("bred_for")
    val bredFor: String,
    @SerializedName("breed_group")
    val breedGroup: String?,
    @SerializedName("height")
    val height: SizeUnit,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("weight")
    val weight: SizeUnit,
    @SerializedName("url")
    val url: String
)

data class SizeUnit(
    val imperial: String,
    val metric: String,
)