package com.example.moviesapi.domain.use_case

import com.example.moviesapi.domain.repository.MoviesRepository
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, isFav: Boolean) {
        repository.updateFavorite(movieId, isFav)
    }
}
