package com.mirdev.capstone.core.domain.usecase

import com.mirdev.capstone.core.data.Resource
import com.mirdev.capstone.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getMovieByQuery(query: String): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun getLocalMovieByQuery(query: String): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}