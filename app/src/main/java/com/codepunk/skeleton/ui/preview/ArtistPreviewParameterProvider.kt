package com.codepunk.skeleton.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.Image

class ArtistPreviewParameterProvider : PreviewParameterProvider<Artist> {
    private val loremIpsum = LoremIpsum().values.first()
    private val imagePreviewProvider = ImagePreviewParameterProvider()

    override val values: Sequence<Artist> = sequenceOf(
        Artist(
            id = 0,
            resourceUrl = "",
            uri = "",
            images = listOf(
                imagePreviewProvider.values.elementAtOrElse(0) { Image() },
            ),
            dataQuality = "",
            name = "Artist One",
            profile = loremIpsum,
            releasesUrl = "",
            urls = listOf(),
            realName = "Artist Number One",
            nameVariations = listOf(),
            aliases = listOf(),
            members = listOf(),
            groups = listOf()
        ),
        Artist(
            id = 0,
            resourceUrl = "",
            uri = "",
            images = listOf(
                imagePreviewProvider.values.elementAtOrElse(1) { Image() },
            ),
            dataQuality = "",
            name = "Artist Two",
            profile = loremIpsum,
            releasesUrl = "",
            urls = listOf(),
            realName = "",
            nameVariations = listOf(),
            aliases = listOf(),
            members = listOf(),
            groups = listOf()
        ),
        Artist(
            id = 0,
            resourceUrl = "",
            uri = "",
            images = listOf(
                imagePreviewProvider.values.elementAtOrElse(2) { Image() },
            ),
            dataQuality = "",
            name = "Artist Three",
            profile = loremIpsum,
            releasesUrl = "",
            urls = listOf(),
            realName = "Artist Number Three",
            nameVariations = listOf(),
            aliases = listOf(),
            members = listOf(),
            groups = listOf()
        ),
    )
}