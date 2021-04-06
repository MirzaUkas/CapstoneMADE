package com.mirdev.capstone.core.utils

import com.mirdev.capstone.core.data.source.local.entity.MovieEntity
import com.mirdev.capstone.core.data.source.remote.response.MovieResponse
import com.mirdev.capstone.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                movieId = it.id ?: 0,
                title = it.title ?: "",
                overview = it.overview ?: "",
                imagePoster = it.posterPath ?: "",
                imageBackdrop = it.backdropPath ?: "",
                like = it.voteCount ?: 0,
                releaseDate = it.releaseDate ?: "",
                voteAverage = it.voteAverage ?: 0.0,
                isFavorite = false,
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                movieId = it.movieId,
                overview = it.overview,
                title = it.title,
                imageBackdrop = it.imageBackdrop,
                imagePoster = it.imagePoster,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                like = it.like,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(it: Movie) = MovieEntity(
        movieId = it.movieId,
        overview = it.overview,
        title = it.title,
        imageBackdrop = it.imageBackdrop,
        imagePoster = it.imagePoster,
        voteAverage = it.voteAverage,
        releaseDate = it.releaseDate,
        like = it.like,
        isFavorite = it.isFavorite
    )
}