package com.example.moviesapi.data.repository

import com.example.moviesapi.data.local.MoviesDatabase
import com.example.moviesapi.data.local.entities.toMovieEntity
import com.example.moviesapi.data.local.entities.toMoviesDomain
import com.example.moviesapi.data.remote.dto.toMovies
import com.example.moviesapi.data.remote.MoviesApi
import com.example.moviesapi.domain.model.MoviesDomain
import com.example.moviesapi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val database: MoviesDatabase
): MoviesRepository {

    override suspend fun getMovies(): Flow<List<MoviesDomain>> {
        return database.dao.getAllMovies().map { entities ->
            entities.map { it.toMoviesDomain() }
        }
    }

    override suspend fun refreshMovies() {
        try {
            // Get current local movies to preserve favorite states
            val currentMovies = database.dao.getAllMovies().first()
            val favoriteMovieIds = currentMovies.filter { it.isFavorite }.map { it.id }.toSet()

            val remoteMovies = api.getMovies().movies
            val movieEntities = remoteMovies.map { moviesDto ->
                val movieDomain = moviesDto.toMovies()
                // preserve favorite state if it exists locally
                val updatedMovieDomain = movieDomain.copy(
                    isFavorite = favoriteMovieIds.contains(movieDomain.id)
                )
                updatedMovieDomain.toMovieEntity()
            }
            database.dao.refreshMovies(movieEntities)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateFavorite(movieId: Int, isFav: Boolean) {
        database.dao.updateFavorite(movieId, isFav)
    }
}