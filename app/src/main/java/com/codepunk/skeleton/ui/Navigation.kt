package com.codepunk.skeleton.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.codepunk.skeleton.ui.screen.artist.ArtistScreen
import com.codepunk.skeleton.ui.screen.HomeScreen
import com.codepunk.skeleton.ui.screen.artist.ArtistViewModel

@Suppress("SpellCheckingInspection")
const val MARILLION = 218108L
const val TAYLOR_SWIFT = 1124645L

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen
    ) {
        composable<Route.HomeScreen> {
            HomeScreen(
                onNavigateToMarillion = {
                    navController.navigate(
                        Route.ArtistScreen(MARILLION)
                    )
                },
                onNavigateToTaylorSwift = {
                    navController.navigate(
                        Route.ArtistScreen(TAYLOR_SWIFT)
                    )
                }
            )
        }

        composable<Route.ArtistScreen> { backStackEntry ->
            // TODO This might not be the cleanest
            val route = backStackEntry.toRoute<Route.ArtistScreen>()
            val artistId = route.artistId
            val viewModel: ArtistViewModel = hiltViewModel()
            ArtistScreen(
                modifier = modifier,
                state = viewModel.state.copy(artistId = artistId)
            ) { event ->
                viewModel.onEvent(event)
            }
        }
    }
}
