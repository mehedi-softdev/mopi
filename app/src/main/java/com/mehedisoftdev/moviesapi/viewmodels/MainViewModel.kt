package com.mehedisoftdev.moviesapi.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mehedisoftdev.moviesapi.models.MovieInfo
import com.mehedisoftdev.moviesapi.models.Search
import com.mehedisoftdev.moviesapi.models.SearchMovies
import com.mehedisoftdev.moviesapi.repository.MovieRepository
import com.mehedisoftdev.moviesapi.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Array.get
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    // to search movies by keyword
    fun searchMovies(searchKey: String) = movieRepository
        .getSearchedMovies(searchKey = searchKey)
        .cachedIn(viewModelScope)

}