package com.codepunk.skeleton.util.url

import com.codepunk.skeleton.R

@Suppress("SpellCheckingInspection")
enum class Domain(
    val domainName: String,
    val aliases: List<String> = emptyList(),
    val iconRef: Int,
    val nameRef: Int
) {
    BANDCAMP(
        domainName = "bandcamp",
        iconRef = R.drawable.ic_url_bandcamp_24,
        nameRef = R.string.url_bandcamp
    ),
    FACEBOOK(
        domainName = "facebook",
        iconRef = R.drawable.ic_url_facebook_24,
        nameRef = R.string.url_facebook
    ),
    INSTAGRAM(
        domainName = "instagram",
        iconRef = R.drawable.ic_url_instagram_24,
        nameRef = R.string.url_instagram
    ),
    PINTEREST(
        domainName = "pinterest",
        iconRef = R.drawable.ic_url_pinterest_24,
        nameRef = R.string.url_pinterest
    ),
    SOUNDCLOUD(
        domainName = "soundcloud",
        iconRef = R.drawable.ic_url_soundcloud_24,
        nameRef = R.string.url_soundcloud
    ),
    THREADS(
        domainName = "threads",
        iconRef = R.drawable.ic_url_threads_24,
        nameRef = R.string.url_threads
    ),
    TIKTOK(
        domainName = "tiktok",
        iconRef = R.drawable.ic_url_tiktok_24,
        nameRef = R.string.url_tiktok
    ),
    TUMBLR(
        domainName = "tumblr",
        iconRef = R.drawable.ic_url_tumblr_24,
        nameRef = R.string.url_tumblr
    ),
    X(
        domainName = "x",
        aliases = listOf("twitter"),
        iconRef = R.drawable.ic_url_x_24,
        nameRef = R.string.url_x
    ),
    WIKIPEDIA(
        domainName = "wikipedia",
        iconRef = R.drawable.ic_url_wikipedia_24,
        nameRef = R.string.url_wikipedia
    ),
    YOUTUBE(
        domainName = "youtube",
        iconRef = R.drawable.ic_url_youtube_24,
        nameRef = R.string.url_youtube
    ),
    OTHER(
        domainName = "",
        iconRef = R.drawable.ic_url_globe_24,
        nameRef = R.string.url_other
    );

    companion object {
        fun fromDomainName(domainName: String) = entries.find {
            it.domainName == domainName || it.aliases.contains(domainName)
        } ?: OTHER
    }
}
