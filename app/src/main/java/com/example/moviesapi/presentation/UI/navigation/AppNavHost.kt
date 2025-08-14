package com.example.moviesapi.presentation.UI.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moviesapi.presentation.MoviesViewModel
import com.example.moviesapi.presentation.UI.MoviesDetails.MoviesDetailsScreen
import com.example.moviesapi.presentation.UI.MoviesScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavRoutes.MoviesList.route
) {
    val viewModel: MoviesViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavRoutes.MoviesList.route) {
            MoviesScreen(
                viewModel = viewModel,
                onMovieClick = { movie ->
                    viewModel.selectMovie(movie)
                    navController.navigate(NavRoutes.MovieDetails.createRoute(movie.id))
                }
            )
        }
        composable(
            route = NavRoutes.MovieDetails.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable

            MoviesDetailsScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}
