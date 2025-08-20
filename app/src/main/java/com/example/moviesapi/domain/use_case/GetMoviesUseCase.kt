package com.example.moviesapi.domain.use_case

import com.example.moviesapi.core.Resource
import com.example.moviesapi.domain.model.MoviesDomain
import com.example.moviesapi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(fetchFromRemote: Boolean = false): Flow<Resource<List<MoviesDomain>>> = flow {
        try {
            emit(Resource.Loading())

            // getting local data
            val localMovies = repository.getMovies().first()

            if (localMovies.isNotEmpty() && !fetchFromRemote) {
                emit(Resource.Success(localMovies))
                return@flow
            }

            if (localMovies.isEmpty()) {
                emit(Resource.Loading())
            }

            // fetch from remote
            repository.refreshMovies()

            val updatedMovies = repository.getMovies().first()
            emit(Resource.Success(updatedMovies))

        } catch (e: HttpException) {
            // if remote fails but we have local data, show local data
            val localMovies = repository.getMovies().first()
            if (localMovies.isNotEmpty()) {
                emit(Resource.Success(localMovies))
            } else {
                emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }
        } catch (e: IOException) {
            // if no internet but we have local data, show local data
            val localMovies = repository.getMovies().first()
            if (localMovies.isNotEmpty()) {
                emit(Resource.Success(localMovies))
            } else {
                emit(Resource.Error("Could not reach server. Check internet connection"))
            }
        }
    }
}