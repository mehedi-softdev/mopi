package com.mehedisoftdev.moviesapi.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehedisoftdev.moviesapi.models.MovieInfo
import com.mehedisoftdev.moviesapi.models.SearchMovies
import com.mehedisoftdev.moviesapi.repository.MovieRepository
import com.mehedisoftdev.moviesapi.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    // other variables
    private var pageNumber: Int = 1 // default page number

    // variables that gives readonly access
    val searchMovieLiveData: LiveData<Resource<SearchMovies>>
        get() = movieRepository.searchMoviesLiveData


    // to search movies by keyword
    fun searchMovies(searchKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.searchMovies(searchKey, pageNumber)
        }
    }

}