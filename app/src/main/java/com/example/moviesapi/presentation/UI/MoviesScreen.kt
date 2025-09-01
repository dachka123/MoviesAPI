package com.example.moviesapi.presentation.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.moviesapi.R
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapi.core.Dimens
import com.example.moviesapi.domain.model.MoviesDomain
import com.example.moviesapi.presentation.MoviesViewModel
import com.example.moviesapi.presentation.UI.MovieScreenComponents.EmptySearchResult
import com.example.moviesapi.presentation.UI.MovieScreenComponents.ErrorScreen
import com.example.moviesapi.presentation.UI.MovieScreenComponents.MoviesListCard
import com.example.moviesapi.presentation.UI.MovieScreenComponents.SearchBar
import com.example.moviesapi.presentation.UI.MovieScreenComponents.ShimmerListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (MoviesDomain) -> Unit
) {
    val state = viewModel.state
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::refreshMovies,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = Dimens.spacing10),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = stringResource(R.string.movies),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = Dimens.spacing24)
                )
            }

            item {
                SearchBar(
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = viewModel::updateSearchQuery,
                    onClearSearch = viewModel::clearSearch,
                    placeholder = "Search movies..."
                )
            }

            when {
                state.isLoading && state.movies.isEmpty() -> {
                    items(3) {
                        ShimmerListItem(
                            isLoading = true,
                        )
                    }
                }

                state.filteredMovies.isEmpty() && state.searchQuery.isNotEmpty() -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            EmptySearchResult()
                        }
                    }
                }

                state.error.isNotEmpty() && state.movies.isEmpty() -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            ErrorScreen(
                                onClick = { viewModel.refreshMovies() }
                            )
                        }
                    }
                }

                else -> {
                    items(
                        items = state.filteredMovies,
                        key = { movie -> movie.id }
                    ) { movie ->
                        MoviesListCard(
                            movies = listOf(movie),
                            onMovieClick = { selectedMovie ->
                                viewModel.selectMovie(selectedMovie)
                                onMovieClick(selectedMovie)
                            }
                        )
                    }
                }
            }
        }
    }
}
