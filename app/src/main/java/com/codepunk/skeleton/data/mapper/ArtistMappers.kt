package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.relation.LocalArtistWithDetails
import com.codepunk.skeleton.data.local.type.EntityDetailType
import com.codepunk.skeleton.data.local.type.EntityDetailType.*
import com.codepunk.skeleton.data.remote.entity.RemoteArtist
import com.codepunk.skeleton.domain.model.Artist

// region Methods

fun RemoteArtist.toLocalArtistWithDetails(): LocalArtistWithDetails = LocalArtistWithDetails(
    artist = LocalArtist(
        id = id,
        name = name,
        resourceUrl = resourceUrl,
        uri = uri,
        releasesUrl = releasesUrl,
        profile = profile,
        dataQuality = dataQuality
    ),
    images = images.map { it.toLocalImage() },
    details = urls.toLocalArtistDetails(id, URL) +
            nameVariations.toLocalArtistDetails(id, NAME_VARIATION),
    aliases = aliases.map { it.toLocalArtistRelationship() },
    members = members.map { it.toLocalArtistRelationship() },
    groups = groups.map { it.toLocalArtistRelationship() }
)

private fun List<String>.toLocalArtistDetails(
    id: Long,
    detailType: EntityDetailType
): List<LocalArtistDetail> = mapIndexed { detailIdx, detail ->
    LocalArtistDetail(
        artistId = id,
        detailType = detailType,
        detailIdx = detailIdx,
        detail = detail
    )
}

private fun RemoteArtist.Relationship.toLocalArtistRelationship(): LocalArtistRelationship =
    LocalArtistRelationship(
        id = id,
        name = name,
        resourceUrl = resourceUrl,
        active = active,
        thumbnailUrl = thumbnailUrl
    )

fun LocalArtistWithDetails.toDomainArtist(): Artist = Artist(
    id = artist.id,
    name = artist.name,
    resourceUrl = artist.resourceUrl,
    uri = artist.uri,
    releasesUrl = artist.releasesUrl,
    images = images.map { it.toDomainImage() },
    profile = artist.profile,
    urls = details.toDomainArtistDetails(URL),
    nameVariations = details.toDomainArtistDetails(NAME_VARIATION),
    aliases = aliases.map { it.toDomainArtistRelationship() },
    members = members.map { it.toDomainArtistRelationship() },
    groups = groups.map { it.toDomainArtistRelationship() },
    dataQuality = artist.dataQuality
)

private fun List<LocalArtistDetail>.toDomainArtistDetails(
    detailType: EntityDetailType
): List<String> = filter { detail ->
    detail.detailType == detailType
}.map { it.detail }

private fun LocalArtistRelationship.toDomainArtistRelationship(): Artist.Relationship =
    Artist.Relationship(
        id = id,
        name = name,
        resourceUrl = resourceUrl,
        active = active,
        thumbnailUrl = thumbnailUrl
    )

// endregion Methods
