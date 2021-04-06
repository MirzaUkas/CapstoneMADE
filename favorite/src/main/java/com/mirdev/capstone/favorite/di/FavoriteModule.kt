package com.mirdev.capstone.favorite.di

import com.mirdev.capstone.favorite.presentation.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}