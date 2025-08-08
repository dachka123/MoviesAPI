package com.example.moviesapi.domain.use_case

import com.example.moviesapi.core.Resource
import com.example.moviesapi.data.remote.dto.toMovies
import com.example.moviesapi.domain.model.MoviesDomain
import com.example.moviesapi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<Resource<List<MoviesDomain>>> = flow{
        try {
            emit(Resource.Loading())
            val movies = repository.getMovies().map { it.toMovies() }
            emit(Resource.Success(movies))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "error occured"))
        }catch (e: IOException){
            emit(Resource.Error("could not reach server. check internet connection"))
        }
    }

}