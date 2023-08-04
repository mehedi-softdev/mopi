package com.mehedisoftdev.moviesapi.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "https://www.omdbapi.com"
    @Volatile
    private var instance: Retrofit? = null

    fun getInstance() : Retrofit {
        if(instance == null) {
            synchronized(this) {
                instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }
        return instance!!
    }
}