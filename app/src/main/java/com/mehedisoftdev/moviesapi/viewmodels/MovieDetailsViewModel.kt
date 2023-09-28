package com.mehedisoftdev.moviesapi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehedisoftdev.moviesapi.models.MovieInfo
import com.mehedisoftdev.moviesapi.repository.MovieRepository
import com.mehedisoftdev.moviesapi.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject constructor(private val movieRepository: MovieRepository)
    : ViewModel() {
    val moviesLiveData: LiveData<Resource<MovieInfo>>
        get() = movieRepository.movieDetailsLiveData

    // gives details information of a movie
    fun getDetailsMovieInfoById(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getDetailsMovieInfoById(movieId)
        }
    }
}