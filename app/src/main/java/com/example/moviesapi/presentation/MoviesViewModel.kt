package com.example.moviesapi.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapi.core.Resource
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

    init {
        getMovies()
    }

    private fun getMovies(){
        getMoviesUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    state = MoviesState(movies = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    state = MoviesState(
                        error = result.message ?: "error occured"
                    )
                }
                is Resource.Loading -> {
                    state = MoviesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshMovies() {
        state = state.copy(
            isLoading = true,
            error = "",
            movies = emptyList()
        )

        getMovies()
    }
}