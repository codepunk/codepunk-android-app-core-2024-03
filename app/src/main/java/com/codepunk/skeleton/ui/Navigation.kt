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
import com.codepunk.skeleton.ui.screen.label.LabelScreen
import com.codepunk.skeleton.ui.screen.label.LabelViewModel

const val TEMP_ARTIST = -1L
@Suppress("SpellCheckingInspection")
const val MARILLION = 218108L
const val TAYLOR_SWIFT = 1124645L
const val ATLANTIC_RECORDS = 681L
const val REPUBLIC_RECORDS = 38017L

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home
    ) {
        composable<Route.Home> {
            HomeScreen(
                onNavigateToArtist = { artistId ->
                    navController.navigate(
                        Route.Artist(artistId)
                    )
                },
                onNavigateToLabel = { labelId ->
                    navController.navigate(
                        Route.Label(labelId)
                    )
                }
            )
        }

        composable<Route.Artist> { backStackEntry ->
            val artist = backStackEntry.toRoute<Route.Artist>()
            val viewModel: ArtistViewModel = hiltViewModel()
            ArtistScreen(
                modifier = modifier,
                artistId = artist.artistId,
                state = viewModel.state
            ) { event ->
                viewModel.onEvent(event)
            }
        }

        composable<Route.Label> { backStackEntry ->
            val label = backStackEntry.toRoute<Route.Label>()
            val viewModel: LabelViewModel = hiltViewModel()
            LabelScreen(
                modifier = modifier,
                labelId = label.labelId,
                state = viewModel.state
            ) { event ->
                viewModel.onEvent(event)
            }
        }
    }

}
