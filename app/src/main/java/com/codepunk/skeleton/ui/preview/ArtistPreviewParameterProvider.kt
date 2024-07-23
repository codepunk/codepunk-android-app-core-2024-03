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
            images = imagePreviewProvider.values.toList(),
            dataQuality = "",
            name = "Artist One",
            profile = loremIpsum,
            releasesUrl = "",
            urls = listOf(
                "https://www.taylorswift.com/",
                "https://www.facebook.com/TaylorSwift",
                "https://www.instagram.com/taylorswift/",
                "https://www.pinterest.com/taylornation13/",
                "https://taylorswift.tumblr.com/",
                "https://twitter.com/taylorswift13",
                "https://twitter.com/taylornation13",
                "https://en.wikipedia.org/wiki/Taylor_Swift",
                "https://www.youtube.com/user/taylorswift",
                "https://soundcloud.com/taylorswiftofficial"
            ),
            realName = "Artist Number One",
            nameVariations = emptyList(),
            aliases = emptyList(),
            members = emptyList(),
            groups = emptyList()
        ),
        Artist(
            id = 0,
            resourceUrl = "",
            uri = "",
            images = imagePreviewProvider.values.toList(),
            dataQuality = "",
            name = "Artist Two",
            profile = loremIpsum,
            releasesUrl = "",
            urls = emptyList(),
            realName = "",
            nameVariations = emptyList(),
            aliases = emptyList(),
            members = emptyList(),
            groups = emptyList()
        ),
        Artist(
            id = 0,
            resourceUrl = "",
            uri = "",
            images = imagePreviewProvider.values.toList(),
            dataQuality = "",
            name = "Artist Three",
            profile = loremIpsum,
            releasesUrl = "",
            urls = emptyList(),
            realName = "Artist Number Three",
            nameVariations = emptyList(),
            aliases = emptyList(),
            members = emptyList(),
            groups = emptyList()
        ),
    )
}