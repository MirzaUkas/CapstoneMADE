package com.mirdev.capstone.movie.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mirdev.capstone.core.domain.model.Movie
import com.mirdev.capstone.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    val favoriteMovie = movieUseCase.getFavoriteMovie().asLiveData()

    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) =
        movieUseCase.setFavoriteMovie(movie, newStatus)
}