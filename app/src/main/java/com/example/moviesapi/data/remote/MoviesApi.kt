package com.example.moviesapi.data.remote

import com.example.moviesapi.data.remote.dto.MoviesResponseDto
import retrofit2.http.GET

interface MoviesApi {

    @GET("6f2ee524-b63d-474b-b0f6-00611182aeaa")
    suspend fun getMovies(): MoviesResponseDto
}