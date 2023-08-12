package com.mehedisoftdev.moviesapi.di

import androidx.lifecycle.ViewModel
import com.mehedisoftdev.moviesapi.views.MainActivity
import com.mehedisoftdev.moviesapi.views.MovieDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface MoviesApplicationComponent {
    fun injectMainActivity(mainActivity: MainActivity)

    fun getMaps(): Map<Class<*>, ViewModel>
    fun injectMovieDetailsActivity(movieDetailsActivity: MovieDetailsActivity)
}