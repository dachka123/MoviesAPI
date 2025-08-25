package com.example.moviesapi.domain.model

import java.io.Serializable

data class MoviesDomain (
    val description: String,
    val id: Int,
    val isFavorite: Boolean = false,
    val poster: String,
    val title: String,
    val year: Int,
    val rating: Double,
    val genre: List<String>
): Serializable