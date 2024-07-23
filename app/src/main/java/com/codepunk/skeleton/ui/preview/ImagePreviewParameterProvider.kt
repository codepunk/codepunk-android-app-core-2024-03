package com.codepunk.skeleton.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.codepunk.skeleton.R
import com.codepunk.skeleton.domain.model.Image
import com.codepunk.skeleton.domain.type.ImageType

class ImagePreviewParameterProvider : PreviewParameterProvider<Image> {
    override val values: Sequence<Image> = sequenceOf(
        Image(
            type = ImageType.PRIMARY,
            uri = R.drawable.img_preview_1_aniket_deole_unsplash.toString(),
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.SECONDARY,
            uri = R.drawable.img_preview_2_daniela_kokina_unsplash.toString(),
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.SECONDARY,
            uri = R.drawable.img_preview_3_diego_jimenez_unsplash.toString(),
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.SECONDARY,
            uri = R.drawable.img_preview_4_johannes_plenio_unsplash.toString(),
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.SECONDARY,
            uri = R.drawable.img_preview_5_kalen_emsley_unsplash.toString(),
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.SECONDARY,
            uri = R.drawable.img_preview_6_tobias_keller_unsplash.toString(),
            width = 640,
            height = 427
        ),
    )
}