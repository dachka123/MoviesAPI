package com.example.moviesapi.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapi.core.Resource
import com.example.moviesapi.domain.model.MoviesDomain
import com.example.moviesapi.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): ViewModel() {

    var state by mutableStateOf(MoviesState())
        private set

    private var _selectedMovie: MoviesDomain? = null
    val selectedMovie: MoviesDomain? get() = _selectedMovie

    fun selectMovie(movie: MoviesDomain) {
        _selectedMovie = movie
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
                    state = state.copy(
                        movies = result.data ?: emptyList(),
                        isLoading = false,
                        isRefreshing = false,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message ?: "An error occurred",
                        isLoading = false,
                        isRefreshing = false,
                        movies = result.data ?: state.movies
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = state.movies.isEmpty(),
                        movies = result.data ?: state.movies
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshMovies() {
        getMovies(fetchFromRemote = true)
    }
}