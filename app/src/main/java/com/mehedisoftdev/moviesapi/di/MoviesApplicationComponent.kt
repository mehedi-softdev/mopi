package com.mehedisoftdev.moviesapi.di

import com.mehedisoftdev.moviesapi.views.MainActivity
import com.mehedisoftdev.moviesapi.views.MovieDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MoviesModule::class])
interface MoviesApplicationComponent {
    fun injectMainActivity(mainActivity: MainActivity)
    fun injectMovieDetailsActivity(movieDetailsActivity: MovieDetailsActivity)
}