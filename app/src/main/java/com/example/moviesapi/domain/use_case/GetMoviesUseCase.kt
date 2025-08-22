package com.example.moviesapi.domain.use_case

import com.example.moviesapi.core.Resource
import com.example.moviesapi.domain.model.MoviesDomain
import com.example.moviesapi.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(fetchFromRemote: Boolean = false): Flow<Resource<List<MoviesDomain>>> = flow {
        try {
            // get current local data
            var localMovies: List<MoviesDomain> = emptyList()

            repository.getMovies().take(1).collect { movies ->
                localMovies = movies
            }

            if (localMovies.isNotEmpty() && !fetchFromRemote) {
                emit(Resource.Success(localMovies))
                return@flow
            }

            // show loading if no local data available
            if (localMovies.isEmpty()) {
                emit(Resource.Loading())
            }

            // fetch from remote
            repository.refreshMovies()

            // emit updated data from repository
            emitAll(
                repository.getMovies()
                    .take(1)
                    .map { updatedMovies ->
                        Resource.Success(updatedMovies)
                    }
            )

        } catch (e: HttpException) {
            // try to fall back to local data
            repository.getMovies().take(1).collect { localMovies ->
                if (localMovies.isNotEmpty()) {
                    emit(Resource.Success(localMovies))
                } else {
                    emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
                }
            }
        } catch (e: IOException) {
            repository.getMovies().take(1).collect { localMovies ->
                if (localMovies.isNotEmpty()) {
                    emit(Resource.Success(localMovies))
                } else {
                    emit(Resource.Error("Could not reach server. Check internet connection"))
                }
            }
        } catch (e: Exception) {
            repository.getMovies().take(1).collect { localMovies ->
                if (localMovies.isNotEmpty()) {
                    emit(Resource.Success(localMovies))
                } else {
                    emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
                }
            }
        }
    }
}