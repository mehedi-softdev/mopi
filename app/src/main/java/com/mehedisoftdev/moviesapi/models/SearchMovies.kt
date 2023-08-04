package com.mehedisoftdev.moviesapi.models

data class SearchMovies(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)