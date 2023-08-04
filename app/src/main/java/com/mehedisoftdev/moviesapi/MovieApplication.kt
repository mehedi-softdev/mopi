package com.mehedisoftdev.moviesapi

import android.app.Application
import com.mehedisoftdev.moviesapi.api.MovieService
import com.mehedisoftdev.moviesapi.api.RetrofitHelper
import com.mehedisoftdev.moviesapi.repository.MovieRepository

class MovieApplication : Application() {
    private lateinit var movieService: MovieService
    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()
        movieService = RetrofitHelper.getInstance().create(MovieService::class.java)
        movieRepository = MovieRepository(applicationContext, movieService)
    }

}