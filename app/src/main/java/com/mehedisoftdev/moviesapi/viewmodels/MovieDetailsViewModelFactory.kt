package com.mehedisoftdev.moviesapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mehedisoftdev.moviesapi.repository.MovieRepository

class MovieDetailsViewModelFactory(private val movieRepository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieRepository) as T
    }
}