package com.codepunk.skeleton.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.codepunk.skeleton.domain.model.Track

class TrackPreviewParameterProvider : PreviewParameterProvider<Track> {
    override val values: Sequence<Track> = sequenceOf(
        Track(
            position = "",
            type = "index",
            title = "Be Hard On Yourself",
            subTracks = listOf(
                Track(
                    position = "A1 i",
                    type = "track",
                    title = "The Tear In The Big Picture",
                    subTracks = emptyList()
                ),
                Track(
                    position = "A1 ii",
                    type = "track",
                    title = "Lust For Luxury",
                    subTracks = emptyList()
                ),
                Track(
                    position = "A1 iii",
                    type = "track",
                    title = "You Can Learn",
                    subTracks = emptyList()
                ),
            )
        ),
        Track(
            position = "",
            type = "index",
            title = "Reprogram The Gene",
            subTracks = listOf(
                Track(
                    position = "A2 i",
                    type = "track",
                    title = "Invincible",
                    subTracks = emptyList()
                ),
                Track(
                    position = "A2 ii",
                    type = "track",
                    title = "Trouble-Free Life",
                    subTracks = emptyList()
                ),
                Track(
                    position = "A2 iii",
                    type = "track",
                    title = "A Cure For Us?",
                    subTracks = emptyList()
                ),
            )
        ),
        Track(
            position = "A3",
            type = "track",
            title = "Only A Kiss (Instr.)",
            subTracks = emptyList()
        ),
        Track(
            position = "A4",
            type = "track",
            title = "Murder Machines",
            subTracks = emptyList()
        ),
        Track(
            position = "B1",
            type = "track",
            title = "The Crow And The Nightingale",
            subTracks = emptyList()
        ),
        Track(
            position = "",
            type = "index",
            title = "Sierra Leone",
            subTracks = listOf(
                Track(
                    position = "B2 i",
                    type = "track",
                    title = "Chance In A Million",
                    subTracks = emptyList()
                ),
                Track(
                    position = "B2 ii",
                    type = "track",
                    title = "The White Sand",
                    subTracks = emptyList()
                ),
                Track(
                    position = "B2 iii",
                    type = "track",
                    title = "The Diamond",
                    subTracks = emptyList()
                ),
                Track(
                    position = "B2 iv",
                    type = "track",
                    title = "The Blue Warm Air",
                    subTracks = emptyList()
                ),
                Track(
                    position = "B2 v",
                    type = "track",
                    title = "More Than Treasure",
                    subTracks = emptyList()
                ),
            )
        ),
        Track(
            position = "",
            type = "index",
            title = "Care",
            subTracks = listOf(
                Track(
                    position = "C1 i",
                    type = "track",
                    title = "Maintenance Drugs",
                    subTracks = emptyList()
                ),
                Track(
                    position = "C1 ii",
                    type = "track",
                    title = "An Hour Before It's Dark",
                    subTracks = emptyList()
                ),
                Track(
                    position = "C1 iii",
                    type = "track",
                    title = "Every Cell",
                    subTracks = emptyList()
                ),
                Track(
                    position = "C1 iv",
                    type = "track",
                    title = "Angels On Earth",
                    subTracks = emptyList()
                ),
            )
        ),
        Track(
            position = "D1",
            type = "index",
            title = "Murder Machines 12\" Remix",
            subTracks = emptyList()
        ),
    )
}