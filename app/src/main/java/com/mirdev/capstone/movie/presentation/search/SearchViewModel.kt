package com.mirdev.capstone.movie.presentation.search

import androidx.lifecycle.ViewModel
import com.mirdev.capstone.core.domain.model.Movie
import com.mirdev.capstone.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getMovie(query: String): Flow<List<Movie>> {
        return movieUseCase.getLocalMovieByQuery(query)
    }
}