package com.example.moviesapi.presentation.UI.MovieScreenComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.moviesapi.core.Dimens
import com.example.moviesapi.domain.model.MoviesDomain

@Composable
fun MoviesListCard(
    movies: List<MoviesDomain>,
    onMovieClick: (MoviesDomain) -> Unit
) {
    if (movies.isNotEmpty()) {
        MovieCard(
            movie = movies.first(),
            onClick = onMovieClick,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF121212))
                .padding(horizontal = Dimens.spacing10)
        )
    }
}