package com.codepunk.skeleton.util.url

import android.content.Context
import android.net.Uri
import com.codepunk.skeleton.R

data class UrlInfo(
    val urlString: String,
    val domain: Domain
) {
    private var _domainString: String? = null
    private var _lastPathSegment: String? = null
    val lastPathSegment: String
        get() = _lastPathSegment
            ?: urlString.getLastPathSegment().apply {
                _lastPathSegment = this
            }

    constructor(
        urlString: String
    ) : this(
        urlString,
        Domain.fromUrlString(urlString)
    )

    fun getDomainString(context: Context): String =
        _domainString
            ?: if (domain == Domain.OTHER) {
                urlString.toDomainString()
            } else {
                context.getString(domain.nameRef)
            }.apply {
                _domainString = this
            }

    @Suppress("SpellCheckingInspection")
    enum class Domain(
        val domains: List<String>,
        val iconRef: Int,
        val nameRef: Int
    ) {
        BANDCAMP(
            listOf("bandcamp"),
            R.drawable.ic_url_bandcamp_24,
            R.string.url_bandcamp
        ),
        FACEBOOK(
            listOf("facebook"),
            R.drawable.ic_url_facebook_24,
            R.string.url_facebook
        ),
        INSTAGRAM(
            listOf("instagram"),
            R.drawable.ic_url_instagram_24,
            R.string.url_instagram
        ),
        PINTEREST(
            listOf("pinterest"),
            R.drawable.ic_url_pinterest_24,
            R.string.url_pinterest
        ),
        SOUNDCLOUD(
            listOf("soundcloud"),
            R.drawable.ic_url_soundcloud_24,
            R.string.url_soundcloud
        ),
        THREADS(
            listOf("threads"),
            R.drawable.ic_url_threads_24,
            R.string.url_threads
        ),
        TUMBLR(
            listOf("tumblr"),
            R.drawable.ic_url_tumblr_24,
            R.string.url_tumblr
        ),
        X(
            listOf("twitter", "x"),
            R.drawable.ic_url_x_24,
            R.string.url_x
        ),
        WIKIPEDIA(
            listOf("wikipedia"),
            R.drawable.ic_url_wikipedia_24,
            R.string.url_wikipedia
        ),
        YOUTUBE(
            listOf("youtube"),
            R.drawable.ic_url_youtube_24,
            R.string.url_youtube
        ),
        OTHER(
            emptyList(),
            R.drawable.ic_url_globe_24,
            R.string.url_other
        );

        companion object {
            fun fromUrlString(urlString: String): Domain {
                val rootDomain = urlString.toRootDomain()
                return entries.find { it.domains.contains(rootDomain) } ?: OTHER
            }

        }
    }

    companion object {
        private const val DOT = "."

        private fun List<String>.getSegment(fromLast: Int) =
            getOrNull(size - 1 - fromLast).orEmpty().lowercase()

        private fun List<String>.rootDomain() = getSegment(1)

        private fun List<String>.topLevelDomain() = getSegment(0)

        private fun String.toRootDomain(): String = try {
            Uri.parse(this).host.orEmpty().split(DOT).rootDomain()
        } catch (e: NullPointerException) {
            ""
        }

        private fun String.toDomainString(): String = try {
            Uri.parse(this).run {
                val hostSegments = host.orEmpty().split(DOT)
                buildString {
                    append(hostSegments.rootDomain())
                    if (isNotEmpty()) append(DOT)
                    append(hostSegments.topLevelDomain())
                }
            }
        } catch (e: NullPointerException) {
            ""
        }

        private fun String.getLastPathSegment(): String = try {
            Uri.parse(this).lastPathSegment.orEmpty()
        } catch (e: NullPointerException) {
            ""
        }
    }

}