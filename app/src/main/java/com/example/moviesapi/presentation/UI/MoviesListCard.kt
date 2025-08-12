package com.example.moviesapi.presentation.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moviesapi.R
import com.example.moviesapi.core.Dimens
import com.example.moviesapi.domain.model.MoviesDomain

@Composable
fun MovieListCard(
    movies: List<MoviesDomain>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Text(
            text = stringResource(R.string.movies),
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = Dimens.spacing24,top = Dimens.spacing34)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.spacing15),
            contentPadding = PaddingValues(Dimens.spacing10),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing20)
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie)
            }
        }
    }
}