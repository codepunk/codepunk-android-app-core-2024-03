package com.codepunk.skeleton.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.RelatedRelease

class RelatedReleasePreviewParameterProvider : PreviewParameterProvider<RelatedRelease> {

    override val values: Sequence<RelatedRelease> = sequenceOf(
        RelatedRelease(
            status = "Accepted",
            type = "release",
            format = "",
            label = "Artist Label",
            title = "Artist Release Number One",
            role = "Main",
            artist = "Artist 1",
            year = 2024,
            thumb = R.drawable.img_preview_1_aniket_deole_unsplash.toString()
        ),
        RelatedRelease(
            status = "Accepted",
            type = "release",
            format = "",
            label = "Artist Label",
            title = "Artist Release Number Two",
            role = "Main",
            artist = "Artist 1",
            year = 2023,
            thumb = R.drawable.img_preview_2_daniela_kokina_unsplash.toString()
        ),
        RelatedRelease(
            status = "Accepted",
            type = "release",
            format = "",
            label = "Artist Label",
            title = "Artist Rel 3",
            role = "Main",
            artist = "Artist 1",
            year = 2022,
            thumb = R.drawable.img_preview_3_diego_jimenez_unsplash.toString()
        ),
        RelatedRelease(
            status = "Accepted",
            type = "release",
            format = "",
            label = "Artist Label",
            title = "Artist Release Number Four",
            role = "Main",
            artist = "Artist 1",
            year = 2021,
            thumb = R.drawable.img_preview_4_johannes_plenio_unsplash.toString()
        ),
        RelatedRelease(
            status = "Accepted",
            type = "release",
            format = "",
            label = "Artist Label",
            title = "Artist Release Number Five",
            role = "Main",
            artist = "Artist 1",
            year = 2020,
            thumb = R.drawable.img_preview_5_kalen_emsley_unsplash.toString()
        ),
        RelatedRelease(
            status = "Accepted",
            type = "release",
            format = "",
            label = "Artist Label",
            title = "Artist Release Number Six",
            role = "Main",
            artist = "Artist 1",
            year = 2018,
            thumb = R.drawable.img_preview_6_tobias_keller_unsplash.toString()
        ),
        RelatedRelease(
            status = "Accepted",
            type = "release",
            format = "",
            label = "Artist Label",
            title = "Artist Release Number Seven",
            role = "Main",
            artist = "Artist 1",
            year = 2017,
            thumb = R.drawable.img_preview_1_aniket_deole_unsplash.toString()
        ),
        RelatedRelease(
            status = "Accepted",
            type = "release",
            format = "",
            label = "Artist Label",
            title = "Artist Release Number Eight",
            role = "Main",
            artist = "Artist 1",
            year = 2016,
            thumb = R.drawable.img_preview_2_daniela_kokina_unsplash.toString()
        )
    )
}