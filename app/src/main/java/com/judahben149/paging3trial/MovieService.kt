package com.judahben149.paging3trial

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun fetchDiscoverMoviesList(@Query("page") pageNumber: Int): Response<DiscoverMoviesResponse>
}