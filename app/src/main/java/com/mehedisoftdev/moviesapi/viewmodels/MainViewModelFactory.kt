package com.mehedisoftdev.moviesapi.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mehedisoftdev.moviesapi.repository.MovieRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(movieRepository) as T
    }

}