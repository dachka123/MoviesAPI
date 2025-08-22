package com.example.moviesapi.presentation.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.moviesapi.R
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapi.core.Dimens
import com.example.moviesapi.domain.model.MoviesDomain
import com.example.moviesapi.presentation.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (MoviesDomain) -> Unit
) {
    val state = viewModel.state
    val pullToRefreshState = rememberPullToRefreshState()

    val isRefreshing = state.isRefreshing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .statusBarsPadding()
    ) {
        Text(
            text = stringResource(R.string.movies),
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = Dimens.spacing24)
        )

        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = isRefreshing,
            onRefresh = {
                viewModel.refreshMovies()
            },
            state = pullToRefreshState,
        ) {
            when {
                state.isLoading && state.movies.isEmpty() -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = Dimens.spacing15),
                        contentPadding = PaddingValues(Dimens.spacing10),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing20)
                    ) {
                        items(3) {
                            ShimmerListItem(
                                isLoading = true,
                            )
                        }
                    }
                }

                state.error.isNotEmpty() && state.movies.isEmpty() -> {
                    ErrorScreen(
                        onClick = {
                            viewModel.refreshMovies()
                        }
                    )
                }

                else -> {
                    MovieListCard(
                        movies = state.movies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}
