package com.example.moviesapi.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapi.core.Resource
import com.example.moviesapi.domain.model.MoviesDomain
import com.example.moviesapi.domain.use_case.GetMoviesUseCase
import com.example.moviesapi.domain.use_case.UpdateFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
): ViewModel() {

    var state by mutableStateOf(MoviesState())
        private set

    private var _selectedMovieId: Int? = null

    val selectedMovie: MoviesDomain?
        get() = _selectedMovieId?.let { id ->
            state.movies.find { it.id == id }
        }

    fun selectMovie(movie: MoviesDomain) {
        _selectedMovieId = movie.id
    }

    init {
        getMovies()
    }

    private fun getMovies(fetchFromRemote: Boolean = false){

        if (fetchFromRemote && state.movies.isNotEmpty()) {
            state = state.copy(isRefreshing = true)
        }

        getMoviesUseCase(fetchFromRemote).onEach { result ->
            when(result){
                is Resource.Success -> {
                    val movies = result.data ?: emptyList()
                    state = state.copy(
                        movies = result.data ?: emptyList(),
                        isLoading = false,
                        isRefreshing = false,
                        error = "",
                        filteredMovies = filterMovies(movies, state.searchQuery)
                    )
                }
                is Resource.Error -> {
                    val movies = result.data ?: state.movies
                    state = state.copy(
                        error = result.message ?: "An error occurred",
                        isLoading = false,
                        isRefreshing = false,
                        movies = result.data ?: state.movies,
                        filteredMovies = filterMovies(movies, state.searchQuery)
                    )
                }
                is Resource.Loading -> {
                    val movies = result.data ?: state.movies
                    state = state.copy(
                        isLoading = state.movies.isEmpty(),
                        movies = result.data ?: state.movies,
                        filteredMovies = filterMovies(movies, state.searchQuery)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshMovies() {
        getMovies(fetchFromRemote = true)
    }

    fun toggleFavorite(movieId: Int, currentState: Boolean) {
        viewModelScope.launch {
            updateFavoriteUseCase(movieId, !currentState)

            val updatedMovies = state.movies.map { movie ->
                //if star clicked
                if (movie.id == movieId) {
                    movie.copy(isFavorite = !currentState)
                } else {
                    //else return movie unchanged
                    movie
                }
            }

            //updating UI
            state = state.copy(
                movies = updatedMovies,
                filteredMovies = filterMovies(updatedMovies, state.searchQuery)
            )
        }
    }

    fun updateSearchQuery(query: String) {
        state = state.copy(
            searchQuery = query,
            filteredMovies = filterMovies(state.movies, query)
        )
    }

    fun clearSearch() {
        state = state.copy(
            searchQuery = "",
            filteredMovies = state.movies
        )
    }

    private fun filterMovies(movies: List<MoviesDomain>, query: String): List<MoviesDomain> {
        return if (query.isBlank()) {
            movies
        } else {
            movies.filter { movie ->
                movie.title.contains(query, ignoreCase = true)
            }
        }
    }
}