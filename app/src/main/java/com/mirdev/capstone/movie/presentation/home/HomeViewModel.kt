package com.mirdev.capstone.movie.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mirdev.capstone.core.domain.usecase.MovieUseCase

class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movie = movieUseCase.getAllMovie().asLiveData()
}