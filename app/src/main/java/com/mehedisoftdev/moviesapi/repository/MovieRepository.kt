package com.mehedisoftdev.moviesapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mehedisoftdev.moviesapi.api.MovieApi
import com.mehedisoftdev.moviesapi.models.MovieInfo
import com.mehedisoftdev.moviesapi.models.Search
import com.mehedisoftdev.moviesapi.paging.MoviePagingSource
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi)
{
    private var _movieDetailsLiveData = MutableLiveData<Resource<MovieInfo>>()
    val movieDetailsLiveData: LiveData<Resource<MovieInfo>>
        get() = _movieDetailsLiveData

    fun getSearchedMovies(searchKey: String) = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 50),
        pagingSourceFactory = { MoviePagingSource(movieApi, searchKey) }
    ).liveData

    // get movie details
    suspend fun getDetailsMovieInfoById(movieId: String) {
        _movieDetailsLiveData.postValue(Resource.Loading())
        try {
            val response = movieApi.getMovieDetailsInfoById(movieId = movieId)
            if(response != null) {
                _movieDetailsLiveData.postValue(Resource.Success(response.body()))
            }
            else {
                _movieDetailsLiveData.postValue(Resource.Error("Something went wrong! Please try again."))
            }
        }
        catch (e: Exception) {
            _movieDetailsLiveData.postValue(Resource.Error(e.message.toString()))
        }
    }
}