package com.mehedisoftdev.moviesapi.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mehedisoftdev.moviesapi.api.MovieApi
import com.mehedisoftdev.moviesapi.models.Search

class MoviePagingSource(private val movieApi: MovieApi, private val searchKey: String) :
    PagingSource<Int, Search>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val position = params.key ?: 1
            val response = movieApi.searchMovies(searchKey = searchKey, position)

            LoadResult.Page(
                data = response.Search,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalResults.toInt()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    }

}