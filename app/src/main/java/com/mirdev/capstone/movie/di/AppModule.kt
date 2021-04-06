package com.mirdev.capstone.movie.di

import com.mirdev.capstone.core.domain.usecase.MovieInteractor
import com.mirdev.capstone.core.domain.usecase.MovieUseCase
import com.mirdev.capstone.movie.presentation.detail.DetailViewModel
import com.mirdev.capstone.movie.presentation.home.HomeViewModel
import com.mirdev.capstone.movie.presentation.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}