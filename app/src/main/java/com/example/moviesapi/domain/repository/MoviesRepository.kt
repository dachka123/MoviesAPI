package com.example.moviesapi.domain.repository

import com.example.moviesapi.domain.model.MoviesDomain
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(): Flow<List<MoviesDomain>>
    suspend fun refreshMovies()
    suspend fun updateFavorite(movieId: Int, isFav: Boolean)

}