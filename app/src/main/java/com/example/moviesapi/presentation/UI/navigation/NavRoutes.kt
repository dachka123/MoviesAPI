package com.example.moviesapi.presentation.UI.navigation


sealed class NavRoutes(val route: String) {
    object MoviesList : NavRoutes("moviesList")
    object MovieDetails : NavRoutes("movieDetails/{movieId}") {
        fun createRoute(movieId: Int) = "movieDetails/$movieId"
    }
}