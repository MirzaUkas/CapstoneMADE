package com.mirdev.capstone.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    val movieId: Int,
    val title: String,
    val overview: String,
    val imagePoster: String,
    val imageBackdrop: String,
    val releaseDate: String,
    val like: Int,
    val voteAverage: Double,
    val isFavorite: Boolean,
) : Parcelable