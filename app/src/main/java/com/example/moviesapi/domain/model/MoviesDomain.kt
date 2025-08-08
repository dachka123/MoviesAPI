package com.example.moviesapi.domain.model

data class MoviesDomain (
    val description: String,
    val id: Int,
    val isFavorite: Boolean,
    val poster: String,
    val title: String,
)