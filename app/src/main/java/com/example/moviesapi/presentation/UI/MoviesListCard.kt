package com.example.moviesapi.presentation.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.moviesapi.core.Dimens
import com.example.moviesapi.domain.model.MoviesDomain

@Composable
fun MovieListCard(
    movies: List<MoviesDomain>,
    onMovieClick: (MoviesDomain) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.spacing15),
            contentPadding = PaddingValues(Dimens.spacing10),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing20)
        ) {
            items(movies) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = onMovieClick
                )
            }
        }
    }
}