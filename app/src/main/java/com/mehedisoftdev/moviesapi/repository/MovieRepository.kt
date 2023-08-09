package com.mehedisoftdev.moviesapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mehedisoftdev.moviesapi.api.MovieApi
import com.mehedisoftdev.moviesapi.models.MovieInfo
import com.mehedisoftdev.moviesapi.models.SearchMovies
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieApi)
{

    //   variable for search result live data
    private var mutableMoviesSearchData = MutableLiveData<Resource<SearchMovies>>()
    val searchMoviesLiveData: LiveData<Resource<SearchMovies>>
        get() = mutableMoviesSearchData
// variable for details info of movie
    private var mutableMovieDetails = MutableLiveData<Resource<MovieInfo>>()
    val movieDetailsLiveData: LiveData<Resource<MovieInfo>>
        get() = mutableMovieDetails


    suspend fun searchMovies(searchKey: String, page: Int) {
        // set loading state
        mutableMoviesSearchData.postValue(Resource.Loading())
        try {
            val response = movieService.searchMovies(searchKey, page)
            if(response != null) {
                mutableMoviesSearchData.postValue(Resource.Success(response.body()))
            }else {
                mutableMoviesSearchData.postValue(Resource.Error("No movies found for $searchKey"))
            }
        }catch (e: Exception) {
            mutableMovieDetails.postValue(Resource.Error(e.message.toString()))
        }
    }

    // get movie details
    suspend fun getDetailsMovieInfoById(movieId: String) {
        mutableMovieDetails.postValue(Resource.Loading())
        try {
            val response = movieService.getMovieDetailsInfoById(movieId = movieId)
            if(response != null) {
                mutableMovieDetails.postValue(Resource.Success(response.body()))
            }
            else {
                mutableMovieDetails.postValue(Resource.Error("Something went wrong! Please try again."))
            }
        }
        catch (e: Exception) {
            mutableMovieDetails.postValue(Resource.Error(e.message.toString()))
        }
    }
}