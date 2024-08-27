package com.codepunk.skeleton.util

import android.net.Uri
import arrow.core.Either
import arrow.core.Ior
import arrow.core.left
import arrow.core.right
import com.codepunk.skeleton.domain.model.ResourceType
import com.codepunk.skeleton.domain.model.ResourceType.ARTIST
import com.codepunk.skeleton.domain.model.ResourceType.LABEL
import org.kefirsf.bb.TextProcessor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BBCodeProcessinator @Inject constructor(
    private val textProcessor: TextProcessor
) {
    private val bbCodeTagRegex = Regex(BBCODE_TAG_PATTERN)
    private val artistNameTagRegex = Regex(ARTIST_NAME_TAG_PATTERN)
    private val artistIdTagRegex = Regex(ARTIST_ID_TAG_PATTERN)
    private val labelNameTagRegex = Regex(LABEL_NAME_TAG_PATTERN)
    private val labelIdTagRegex = Regex(LABEL_ID_TAG_PATTERN)
    private val customHrefRegex = Regex(CUSTOM_HREF_PATTERN)

    // TODO What should be suspend? Maybe all of it but that would require not using replace() :'(

    suspend fun process(
        bbCodeString: String,
        lookup: suspend (ResourceType, Either<Long, String>) -> Ior<Long, String> = { _, value ->
            value.toIor()
        }
    ): String {
        val updatedUrls = bbCodeTagRegex.replaceSuspend(bbCodeString) { matchResult ->
            val substring = bbCodeString.substring(matchResult.range)
            artistNameTagRegex.extractName(substring, ARTIST_NAME_GROUP)?.let { artistName ->
                buildUrlString(ARTIST, lookup(ARTIST, artistName.right()))
            } ?: artistIdTagRegex.extractId(substring, ARTIST_ID_GROUP)?.let { artistId ->
                buildUrlString(ARTIST, lookup(ARTIST, artistId.left()))
            } ?: labelNameTagRegex.extractName(substring, LABEL_NAME_GROUP)?.let { labelName ->
                buildUrlString(LABEL, lookup(LABEL, labelName.right()))
            } ?: labelIdTagRegex.extractId(substring, LABEL_ID_GROUP)?.let { labelId ->
                buildUrlString(LABEL, lookup(LABEL, labelId.left()))
            } ?: substring
        }
        val updatedHtml = customHrefRegex.replace(
            textProcessor.process(updatedUrls)
        ) { matchResult ->
            Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendEncodedPath(matchResult.groups[ENTITY_GROUP]?.value.orEmpty())
                .appendEncodedPath(matchResult.groups[TYPE_GROUP]?.value.orEmpty())
                .appendEncodedPath(matchResult.groups[PATH_GROUP]?.value.orEmpty())
                .build()
                .run { "<a href=\"$this\">" }
        }
        return updatedHtml
    }

    private fun buildUrlString(
        resourceType: ResourceType,
        value: Ior<Long, String>
    ): String {
        val builder = Uri.parse(URI_BASE)
            .buildUpon()
            .appendPath(resourceType.description)
        val name = value.fold(
            fa = { id ->
                builder.appendPath(ID).appendPath(id.toString())
                ""
            },
            fb = { name ->
                builder.appendPath(NAME).appendPath(name)
                name
            },
            fab = { id, name ->
                builder.appendPath(ID).appendPath(id.toString())
                name
            }
        )
        return "[url=${builder.build()}]$name[/url]"
    }

    private fun Regex.extractName(string: String, groupName: String): String? =
        matchEntire(string)?.groups?.get(groupName)?.value

    private fun Regex.extractId(string: String, groupName: String): Long? =
        matchEntire(string)?.groups?.get(groupName)?.value?.toLongOrNull()

    companion object {
        private const val SCHEME = "codepunk"
        private const val AUTHORITY = "discogs"
        private const val URI_BASE = "/discogs.codepunk"
        private const val NAME = "name"
        private const val ID = "id"
        private const val ARTIST_NAME_GROUP = "artistName"
        private const val ARTIST_ID_GROUP = "artistId"
        private const val LABEL_NAME_GROUP = "labelName"
        private const val LABEL_ID_GROUP = "labelId"
        private const val ENTITY_GROUP = "entity"
        private const val TYPE_GROUP = "type"
        private const val PATH_GROUP = "path"
        private const val BBCODE_TAG_PATTERN = "\\[.*?]"
        private const val ARTIST_NAME_TAG_PATTERN = "^\\[a=(?<$ARTIST_NAME_GROUP>.*?)]$"
        private const val ARTIST_ID_TAG_PATTERN = "^\\[a(?<$ARTIST_ID_GROUP>.\\d*?)]$"
        private const val LABEL_NAME_TAG_PATTERN = "^\\[l=(?<$LABEL_NAME_GROUP>.*?)]$"
        private const val LABEL_ID_TAG_PATTERN = "^\\[l(?<$LABEL_ID_GROUP>.\\d*?)]$"
        private const val CUSTOM_HREF_PATTERN =
            "<a href=\"/discogs\\.codepunk" +
                    "/(?<$ENTITY_GROUP>.*?)" +
                    "/(?<$TYPE_GROUP>.*?)" +
                    "/(?<$PATH_GROUP>.*?)\">"
    }

}