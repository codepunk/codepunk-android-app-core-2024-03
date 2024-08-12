package com.codepunk.skeleton.ui.preview

import android.text.Html
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.codepunk.skeleton.domain.model.Artist

class ArtistPreviewParameterProvider : PreviewParameterProvider<Artist> {
    private val loremIpsumHtml = Html.fromHtml(
        LoremIpsum().values.first(),
        Html.FROM_HTML_MODE_COMPACT
    ).toString()
    private val imagePreviewProvider = ImagePreviewParameterProvider()

    override val values: Sequence<Artist> = sequenceOf(
        Artist(
            id = 0,
            resourceUrl = "",
            uri = "",
            images = imagePreviewProvider.values.toList(),
            dataQuality = "",
            name = "Artist One",
            profile = loremIpsumHtml,
            releasesUrl = "",
            urls = listOf(
                "https://www.artist1.com/",
                "https://www.artist1.co.uk/",
                "https://www.facebook.com/artist1",
                "https://www.instagram.com/artist1/",
                "https://www.pinterest.com/artist1/",
                "https://artist1.tumblr.com/",
                "https://twitter.com/artist1",
                "https://twitter.com/artist1fans",
                "https://en.wikipedia.org/wiki/Artist_One",
                "https://www.youtube.com/user/artist1",
                "https://soundcloud.com/artist1"
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
            profile = loremIpsumHtml,
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
            profile = loremIpsumHtml,
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