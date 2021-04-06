package com.mirdev.capstone.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,

    @ColumnInfo(name = "like")
    var like: Int,

    @ColumnInfo(name = "image_poster")
    var imagePoster: String,

    @ColumnInfo(name = "image_backdrop")
    var imageBackdrop: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)