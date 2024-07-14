package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalRelatedArtist
import com.codepunk.skeleton.data.local.entity.LocalRelatedArtist.RelationType
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail.DetailType
import com.codepunk.skeleton.data.local.relation.LocalArtistWithDetails
import com.codepunk.skeleton.data.local.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.remote.entity.RemoteArtist
import com.codepunk.skeleton.data.remote.entity.RemoteRelatedArtist
import com.codepunk.skeleton.domain.model.Artist
import com.codepunk.skeleton.domain.model.RelatedArtist

fun RemoteArtist.toLocalArtist(
    resourceId: Long = 0L
): LocalArtist =
    LocalArtist(
        artistId = id,
        resourceId = resourceId,
        realName = realName,
        name = name,
        profile = profile,
        releasesUrl = releasesUrl
    )

fun RemoteArtist.toLocal(
    resourceId: Long = 0L
): LocalResourceAndArtist =
    LocalResourceAndArtist(
        resource = toLocalResource(resourceId),
        artistWithDetails = LocalArtistWithDetails(
            artist = toLocalArtist(resourceId),
            images = images.mapIndexed { imageIdx, image ->
                image.toLocal(imageIdx = imageIdx)
            },
            details = urls.mapIndexed { detailIdx, url ->
                LocalResourceDetail(resourceId, DetailType.URL, detailIdx, url)
            } + nameVariations.mapIndexed { detailIdx, name ->
                LocalResourceDetail(resourceId, DetailType.NAME_VARIATION, detailIdx, name)
            },
            relatedArtists = aliases.mapIndexed { artistIdx, relatedArtist ->
                relatedArtist.toLocal(resourceId = resourceId, type = RelationType.ALIAS)
            } + members.mapIndexed { artistIdx, relatedArtist ->
                relatedArtist.toLocal(resourceId = resourceId, type = RelationType.MEMBER)
            } + groups.mapIndexed { artistIdx, relatedArtist ->
                relatedArtist.toLocal(resourceId = resourceId, type = RelationType.GROUP)
            }
        )
    )

fun LocalResourceAndArtist.toDomain(): Artist =
    Artist(
        id = artistWithDetails.artist.artistId,
        resourceUrl = resource.resourceUrl,
        uri = resource.uri,
        images = artistWithDetails.images.map { it.toDomain() },
        dataQuality = resource.dataQuality,
        name = artistWithDetails.artist.name,
        profile = artistWithDetails.artist.profile,
        releasesUrl = artistWithDetails.artist.releasesUrl,
        realName = artistWithDetails.artist.realName,
        urls = artistWithDetails.details.filter {
            it.type == DetailType.URL
        }.map { it.detail },
        nameVariations = artistWithDetails.details.filter {
            it.type == DetailType.NAME_VARIATION
        }.map { it.detail },
        aliases = artistWithDetails.relatedArtists.filter {
            it.type == RelationType.ALIAS
        }.map { it.toDomain() },
        members = artistWithDetails.relatedArtists.filter {
            it.type == RelationType.MEMBER
        }.map { it.toDomain() },
        groups = artistWithDetails.relatedArtists.filter {
            it.type == RelationType.GROUP
        }.map { it.toDomain() },
    )

fun RemoteRelatedArtist.toLocal(
    relatedArtistId: Long = 0L,
    resourceId: Long = 0L,
    type: RelationType
): LocalRelatedArtist =
    LocalRelatedArtist(
        relatedArtistId = relatedArtistId,
        resourceId = resourceId,
        type = type,
        artistId = artistId,
        name = name,
        resourceUrl = resourceUrl,
        active = active,
        thumbnailUrl = thumbnailUrl
    )

fun LocalRelatedArtist.toDomain(): RelatedArtist =
    RelatedArtist(
        artistId = artistId,
        name = name,
        resourceUrl = resourceUrl,
        active = active,
        thumbnailUrl = thumbnailUrl
    )
