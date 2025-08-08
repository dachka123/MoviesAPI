package com.example.moviesapi.data.repository

import com.example.moviesapi.data.remote.MoviesApi
import com.example.moviesapi.data.remote.dto.MoviesDto
import com.example.moviesapi.domain.repository.MoviesRepository
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesApi
): MoviesRepository {
    override suspend fun getMovies(): List<MoviesDto> {
        return api.getMovies().movies
    }
}