package com.mirdev.capstone.core.domain.usecase

import com.mirdev.capstone.core.data.Resource
import com.mirdev.capstone.core.domain.model.Movie
import com.mirdev.capstone.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovie() = movieRepository.getAllMovie()

    override fun getMovieByQuery(query: String): Flow<Resource<List<Movie>>> =
        movieRepository.getMovieByQuery(query)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun getLocalMovieByQuery(query: String): Flow<List<Movie>> =
        movieRepository.getLocalMovieByQuery(query)

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)
}