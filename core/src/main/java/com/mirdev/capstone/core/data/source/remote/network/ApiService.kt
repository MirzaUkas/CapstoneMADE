package com.mirdev.capstone.core.data.source.remote.network

import com.mirdev.capstone.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/3/trending/movie/week")
    suspend fun getList(): ListMovieResponse

    @GET("/3/search/movie")
    suspend fun getListByQuery(@Query("query") query: String): ListMovieResponse
}