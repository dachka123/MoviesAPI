package com.example.moviesapi.data.remote.dto

import com.example.moviesapi.domain.model.MoviesDomain

data class MoviesDto(
    val description: String,
    val genre: List<String>,
    val id: Int,
    val isFavorite: Boolean,
    val poster: String,
    val rating: Double,
    val title: String,
    val year: Int
)

fun MoviesDto.toMovies(): MoviesDomain{
    return MoviesDomain(
        description = description,
        id = id,
        isFavorite = isFavorite,
        poster = poster,
        title = title,
        year = year,
        genre = genre,
        rating = rating,
    )
}

data class MoviesResponseDto(
    val movies: List<MoviesDto>
)
