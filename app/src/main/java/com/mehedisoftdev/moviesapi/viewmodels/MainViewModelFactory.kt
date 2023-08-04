package com.mehedisoftdev.moviesapi.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mehedisoftdev.moviesapi.repository.MovieRepository

class MainViewModelFactory(
    private val context: Context,
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context, movieRepository) as T
    }

}