package com.codepunk.skeleton.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.codepunk.skeleton.domain.model.Image
import com.codepunk.skeleton.domain.type.ImageType

class ImagePreviewParameterProvider : PreviewParameterProvider<Image> {
    override val values: Sequence<Image> = sequenceOf(
        Image(
            type = ImageType.PRIMARY,
            uri = "android.resource://com.codepunk.skeleton/drawable/img_preview_1_aniket_deole_unsplash",
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.PRIMARY,
            uri = "android.resource://com.codepunk.skeleton/drawable/img_preview_2_diego_jimenez_unsplash",
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.PRIMARY,
            uri = "android.resource://com.codepunk.skeleton/drawable/img_preview_3_johannes_plenio_unsplash",
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.SECONDARY,
            uri = "android.resource://com.codepunk.skeleton/drawable/img_preview_4_kalen_emsley_unsplash",
            width = 640,
            height = 427
        ),
        Image(
            type = ImageType.SECONDARY,
            uri = "android.resource://com.codepunk.skeleton/drawable/img_preview_5_tobias_keller_unsplash",
            width = 640,
            height = 427
        ),
    )
}