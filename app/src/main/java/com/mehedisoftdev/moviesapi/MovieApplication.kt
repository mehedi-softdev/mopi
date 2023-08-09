package com.mehedisoftdev.moviesapi

import android.app.Application
import com.mehedisoftdev.moviesapi.di.DaggerMoviesApplicationComponent
import com.mehedisoftdev.moviesapi.di.MoviesApplicationComponent

class MovieApplication : Application() {
    lateinit var applicationComponent: MoviesApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerMoviesApplicationComponent.builder().build()
    }

}