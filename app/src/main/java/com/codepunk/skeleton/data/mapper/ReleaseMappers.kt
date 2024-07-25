package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalCredit.CreditType
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel.RelationType
import com.codepunk.skeleton.data.local.entity.LocalRelatedRelease
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail.DetailType
import com.codepunk.skeleton.data.local.relation.LocalReleaseWithDetails
import com.codepunk.skeleton.data.local.relation.LocalResourceAndRelease
import com.codepunk.skeleton.data.remote.entity.RemoteRelatedRelease
import com.codepunk.skeleton.data.remote.entity.RemoteRelease
import com.codepunk.skeleton.domain.model.RelatedRelease
import com.codepunk.skeleton.domain.model.Release

fun RemoteRelease.toLocalRelease(
    resourceId: Long = 0L
): LocalRelease =
    LocalRelease(
        releaseId = id,
        resourceId = resourceId,
        title = title,
        year = year,
        numForSale = numForSale,
        lowestPrice = lowestPrice,
        status = status,
        artistsSort = artistsSort,
        formatQuantity = formatQuantity,
        dateAdded = dateAdded,
        dateChanged = dateChanged,
        masterId = masterId,
        masterUrl = masterUrl,
        country = country,
        released = released,
        notes = notes,
        releasedFormatted = releasedFormatted,
        thumb = thumb
    )

fun RemoteRelease.toLocal(
    resourceId: Long = 0L
): LocalResourceAndRelease =
    LocalResourceAndRelease(
        resource = toLocalResource(resourceId),
        releaseWithDetails = LocalReleaseWithDetails(
            release = toLocalRelease(resourceId),
            images = images.mapIndexed { imageIdx, image ->
                image.toLocal(imageIdx = imageIdx)
            },
            details = genres.mapIndexed { detailIdx, url ->
                LocalResourceDetail(resourceId, DetailType.GENRE, detailIdx, url)
            } + styles.mapIndexed { detailIdx, name ->
                LocalResourceDetail(resourceId, DetailType.STYLE, detailIdx, name)
            },
            trackList = trackList.toLocal(),
            credits = artists.map {
                it.toLocal(type = CreditType.ARTIST)
            } + extraArtists.map {
                it.toLocal(type = CreditType.EXTRA_ARTIST)
            },
            videos = videos.map { it.toLocal() },
            relatedLabels = labels.map {
                it.toLocal(resourceId = resourceId, type = RelationType.LABEL)
            } + series.map {
                it.toLocal(resourceId = resourceId, type = RelationType.SERIES)
            } + companies.map {
                it.toLocal(resourceId = resourceId, type = RelationType.COMPANY)
            },
            formats = formats.mapIndexed { formatIdx, format ->
                format.toLocal(releaseId = id, formatIdx = formatIdx)
            },
            identifiers = identifiers.mapIndexed { identifierIdx, identifier ->
                identifier.toLocal(releaseId = id, identifierIdx = identifierIdx)
            }
        )
    )

fun LocalResourceAndRelease.toDomain(): Release =
    Release(
        id = releaseWithDetails.release.releaseId,
        resourceUrl = resource.resourceUrl,
        uri = resource.uri,
        images = releaseWithDetails.images.map { it.toDomain() },
        dataQuality = resource.dataQuality,
        title = releaseWithDetails.release.title,
        genres = releaseWithDetails.details.filter {
            it.type == DetailType.GENRE
        }.map { it.detail },
        styles = releaseWithDetails.details.filter {
            it.type == DetailType.STYLE
        }.map { it.detail },
        year = releaseWithDetails.release.year,
        numForSale = releaseWithDetails.release.numForSale,
        lowestPrice = releaseWithDetails.release.lowestPrice,
        trackList = releaseWithDetails.trackList.toDomain(),
        artists = releaseWithDetails.credits.filter {
            it.type == CreditType.ARTIST
        }.map { it.toDomain() },
        videos = releaseWithDetails.videos.map { it.toDomain() },
        status = releaseWithDetails.release.status,
        artistsSort = releaseWithDetails.release.artistsSort,
        labels = releaseWithDetails.relatedLabels.filter {
            it.type == RelationType.LABEL
        }.map { it.toDomain() },
        series = releaseWithDetails.relatedLabels.filter {
            it.type == RelationType.SERIES
        }.map { it.toDomain() },
        companies = releaseWithDetails.relatedLabels.filter {
            it.type == RelationType.COMPANY
        }.map { it.toDomain() },
        formats = releaseWithDetails.formats.map { it.toDomain() },
        formatQuantity = releaseWithDetails.release.formatQuantity,
        dateAdded = releaseWithDetails.release.dateAdded,
        dateChanged = releaseWithDetails.release.dateChanged,
        masterId = releaseWithDetails.release.masterId,
        masterUrl = releaseWithDetails.release.masterUrl,
        country = releaseWithDetails.release.country,
        released = releaseWithDetails.release.released,
        notes = releaseWithDetails.release.notes,
        releasedFormatted = releaseWithDetails.release.releasedFormatted,
        identifiers = releaseWithDetails.identifiers.map { it.toDomain() },
        extraArtists = releaseWithDetails.credits.filter {
            it.type == CreditType.EXTRA_ARTIST
        }.map { it.toDomain() },
        thumb = releaseWithDetails.release.thumb
    )

fun RemoteRelatedRelease.toLocal(
    relatedReleaseId: Long = 0L,
    resourceId: Long = 0L
): LocalRelatedRelease = LocalRelatedRelease(
    relatedReleaseId = relatedReleaseId,
    resourceId = resourceId,
    releaseId = releaseId,
    resourceUrl = resourceUrl,
    status = status,
    type = type,
    format = format,
    label = label,
    title = title,
    role = role,
    artist = artist,
    year = year,
    thumb = thumb
)

fun LocalRelatedRelease.toDomain(): RelatedRelease = RelatedRelease(
    releaseId = releaseId,
    resourceUrl = resourceUrl,
    status = status,
    type = type,
    format = format,
    label = label,
    title = title,
    role = role,
    artist = artist,
    year = year,
    thumb = thumb

)