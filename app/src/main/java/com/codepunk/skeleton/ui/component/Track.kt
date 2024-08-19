package com.codepunk.skeleton.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Credit
import com.codepunk.skeleton.domain.model.Track
import com.codepunk.skeleton.ui.preview.TrackPreviewParameterProvider
import com.codepunk.skeleton.ui.theme.SkeletonTheme
import com.codepunk.skeleton.ui.theme.largePadding
import com.codepunk.skeleton.ui.theme.mediumPadding
import com.codepunk.skeleton.util.parseElapsedTimeString

@Composable
fun Track(
    modifier: Modifier = Modifier,
    track: Track,
    level: Int = 0
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = largePadding.times(level))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (track.position.isNotBlank()) {
                Text(
                    text = track.position,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.width(mediumPadding),
                    textAlign = TextAlign.Center,
                    text = "•"
                )
            }
            Text(text = track.title)
        }

        track.extraArtists?.run {
            val credits = extraArtistsToString(this)
            if (credits.isNotBlank()) {
                Text(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.track_list_indent))
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    text = credits
                )
            }
        }

    }
}

private fun extraArtistsToString(
    extraArtists: List<Credit>
): String = extraArtists.groupBy(
    keySelector = { it.role },
    valueTransform = { it.name }
).mapValues { (_, artists) ->
    artists.sorted().joinToString()
}.map { (role, artists) ->
    "$role: $artists"
}.joinToString(separator = "\n")

@Preview
@Composable
fun TrackPreviewDark(
    @PreviewParameter(
        TrackPreviewParameterProvider::class
    ) track: Track
) {
    SkeletonTheme(darkTheme = true) {
        Surface {
            Track(
                track = track
            )
        }
    }
}

@Suppress("SpellCheckingInspection")
@Preview
@Composable
fun ExtraArtistsTrackPreviewDark(

) {
    val track = Track(
        position = "1",
        type = "track",
        title = "Fortnight",
        extraArtists = listOf(
            Credit(
                artistId = 4016434,
                name = "Post Malone",
                role = "Featuring",
            ),
            Credit(
                artistId = 4964238,
                name = "Austin Post",
                role = "Written-By",
            ),
            Credit(
                artistId = 585619,
                name = "Jack Antonoff",
                role = "Written-By",
            ),
        ),
        duration = parseElapsedTimeString("3:48"),
    )
    SkeletonTheme(darkTheme = true) {
        Surface {
            Track(
                track = track
            )
        }
    }
}