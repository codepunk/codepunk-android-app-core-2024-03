package com.codepunk.skeleton.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Credit

class CreditPreviewParameterProvider : PreviewParameterProvider<Credit> {
    override val values: Sequence<Credit> = sequenceOf(
        Credit(
            name = "Artist One",
            role = "Vocals",
            thumbnailUrl = R.drawable.img_preview_1_aniket_deole_unsplash.toString()
        ),
        Credit(
            name = "Artist Two feat. Artist Seven",
            role = "Keyboards",
            thumbnailUrl = R.drawable.img_preview_2_daniela_kokina_unsplash.toString()
        ),
        Credit(
            name = "Artist Three With a Really, Really Long Name",
            role = "Backup vocals",
            thumbnailUrl = R.drawable.img_preview_3_diego_jimenez_unsplash.toString()
        ),
        Credit(
            name = "Artist Four",
            role = "Guitars",
            thumbnailUrl = R.drawable.img_preview_4_johannes_plenio_unsplash.toString()
        ),
        Credit(
            name = "Artist Five",
            role = "Drums",
            thumbnailUrl = R.drawable.img_preview_5_kalen_emsley_unsplash.toString()
        ),
        Credit(
            name = "Artist Six",
            role = "Bass",
            thumbnailUrl = R.drawable.img_preview_6_tobias_keller_unsplash.toString()
        )
    )
}