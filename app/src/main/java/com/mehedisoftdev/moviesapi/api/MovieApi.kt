package com.mehedisoftdev.moviesapi.api

import com.mehedisoftdev.moviesapi.models.MovieInfo
import com.mehedisoftdev.moviesapi.models.SearchMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val apiKey: String = "c069f0ae"

interface MovieApi {
    // search movies
    @GET("/?apikey=$apiKey")
    suspend fun searchMovies(
        @Query("s") searchKey: String,
        @Query("page") pageNumber: Int
    ): SearchMovies

    // get details of movie
    @GET("/?apikey=$apiKey")
    suspend fun getMovieDetailsInfoById(
        @Query("i") movieId: String
    ): Response<MovieInfo>
}