package br.com.nglauber.jetpackcomposeplayground.paging.model.api

import br.com.nglauber.jetpackcomposeplayground.paging.model.Response
import br.com.nglauber.jetpackcomposeplayground.util.MARVEL_API_KEY
import br.com.nglauber.jetpackcomposeplayground.util.MARVEL_PRIVATE_KEY
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

interface MarvelApi {
    @GET("characters")
    suspend fun allCharacters(@Query("offset") offset: Int? = 0): Response

    companion object {
        fun getService(): MarvelApi {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val ts =
                    (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", MARVEL_API_KEY)
                    .addQueryParameter("ts", ts)
                    .addQueryParameter("hash", md5("$ts$MARVEL_PRIVATE_KEY$MARVEL_API_KEY"))
                    .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

            return retrofit.create(MarvelApi::class.java)
        }

        private fun md5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray()))
                .toString(16)
                .padStart(32, '0')
        }
    }
}