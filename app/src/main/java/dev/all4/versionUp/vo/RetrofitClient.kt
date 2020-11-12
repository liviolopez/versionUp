package dev.all4.versionUp.vo

import com.google.gson.GsonBuilder
import dev.all4.versionUp.domain.WebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Livio Lopez on 11/12/20.
 */
object RetrofitClient {
    val webService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}