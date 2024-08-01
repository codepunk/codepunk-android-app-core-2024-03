package com.codepunk.skeleton.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.RelatedArtist

class RelatedArtistPreviewParameterProvider : PreviewParameterProvider<RelatedArtist> {
    override val values: Sequence<RelatedArtist> = sequenceOf(
        RelatedArtist(
            name = "Artist One",
            thumbnailUrl = R.drawable.img_preview_1_aniket_deole_unsplash.toString()
        ),
        RelatedArtist(
            name = "Artist Two feat. Artist Seven",
            thumbnailUrl = R.drawable.img_preview_2_daniela_kokina_unsplash.toString()
        ),
        RelatedArtist(
            name = "Artist Three With a Really, Really Long Name",
            thumbnailUrl = R.drawable.img_preview_3_diego_jimenez_unsplash.toString()
        ),
        RelatedArtist(
            name = "Artist Four",
            thumbnailUrl = R.drawable.img_preview_4_johannes_plenio_unsplash.toString()
        ),
        RelatedArtist(
            name = "Artist Five",
            thumbnailUrl = R.drawable.img_preview_5_kalen_emsley_unsplash.toString()
        ),
        RelatedArtist(
            name = "Artist Six",
            thumbnailUrl = R.drawable.img_preview_6_tobias_keller_unsplash.toString()
        )
    )
}