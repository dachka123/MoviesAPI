package com.example.moviesapi.domain.repository

import com.example.moviesapi.data.remote.dto.MoviesDto

interface MoviesRepository {

    suspend fun getMovies(): List<MoviesDto>
}