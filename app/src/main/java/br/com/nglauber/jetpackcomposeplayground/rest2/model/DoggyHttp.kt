package br.com.nglauber.jetpackcomposeplayground.rest2.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object DoggyHttp {
    private const val DOGGY_JSON_URL =
        "https://raw.githubusercontent.com/nglauber/JetpackComposePlayground/master/misc/dogs.json"

    fun loadDogsGson(): List<Doggy> {
        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url(DOGGY_JSON_URL)
            .build()
        val response = client.newCall(request).execute()
        val json = response.body?.string()
        val gson = Gson()
        val itemType = object : TypeToken<List<Doggy>>() {}.type
        return gson.fromJson(json, itemType)
    }
}