package com.codepunk.skeleton.ui.screen.artist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.codepunk.skeleton.core.loginator.Loginator

@Composable
fun ArtistScreen(
    modifier: Modifier = Modifier,
    state: ArtistScreenState,
    onEvent: (ArtistScreenEvent) -> Unit
) {
    Loginator.d { "state = $state" }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // TODO This might not be the cleanest
        LaunchedEffect(Unit) {
            Loginator.d { "Launched effect!" }
            onEvent(ArtistScreenEvent.LoadArtist(state.artistId))
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.artist?.name
                    ?: if (state.isLoading) "Loading..." else "[Unknown]",
                modifier = modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}
