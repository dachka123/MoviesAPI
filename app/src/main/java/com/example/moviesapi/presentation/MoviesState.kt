package com.example.moviesapi.presentation

import com.example.moviesapi.domain.model.MoviesDomain

data class MoviesState (
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val movies: List<MoviesDomain> = emptyList(),
    val error: String = "",
    val searchQuery: String = "",
    val filteredMovies: List<MoviesDomain> = emptyList()
)