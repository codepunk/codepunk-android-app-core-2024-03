package com.codepunk.skeleton.data.mapper

import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.relation.LocalArtistWithDetails
import com.codepunk.skeleton.data.local.type.EntityDetailType
import com.codepunk.skeleton.data.local.type.EntityDetailType.*
import com.codepunk.skeleton.data.local.type.ArtistRelationshipType
import com.codepunk.skeleton.data.local.type.ArtistRelationshipType.*
import com.codepunk.skeleton.data.remote.entity.RemoteArtist
import com.codepunk.skeleton.domain.model.Artist

// region Methods

fun RemoteArtist.toLocalArtistWithDetails(): LocalArtistWithDetails = LocalArtistWithDetails(
    artist = LocalArtist(
        id = this.id,
        name = this.name,
        resourceUrl = this.resourceUrl,
        uri = this.uri,
        releasesUrl = this.releasesUrl,
        profile = this.profile,
        dataQuality = this.dataQuality
    ),
    images = this.images.map { it.toLocalImage() },
    details = this.urls.toLocalArtistDetails(this.id, URL) +
            this.nameVariations.toLocalArtistDetails(this.id, NAME_VARIATION),
    relationships = this.aliases.toLocalArtistRelationships(this.id, ALIAS) +
            this.members.toLocalArtistRelationships(this.id, MEMBER) +
            this.groups.toLocalArtistRelationships(this.id, GROUP)
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

private fun List<RemoteArtist.Relationship>.toLocalArtistRelationships(
    id: Long,
    relationshipType: ArtistRelationshipType
): List<LocalArtistRelationship> = mapIndexed { relationshipIdx, relationship ->
    LocalArtistRelationship(
        parentId = id,
        relationshipType = relationshipType,
        relationshipIdx = relationshipIdx,
        childId = relationship.id,
        name = relationship.name,
        resourceUrl = relationship.resourceUrl,
        active = relationship.active,
        thumbnailUrl = relationship.thumbnailUrl
    )
}

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
    aliases = relationships.toDomainArtistRelationships(ALIAS),
    members = relationships.toDomainArtistRelationships(MEMBER),
    groups = relationships.toDomainArtistRelationships(GROUP),
    dataQuality = artist.dataQuality
)

private fun List<LocalArtistDetail>.toDomainArtistDetails(
    detailType: EntityDetailType
): List<String> = filter { detail ->
    detail.detailType == detailType
}.map { it.detail }

private fun List<LocalArtistRelationship>.toDomainArtistRelationships(
    relationshipType: ArtistRelationshipType
): List<Artist.Relationship> = filter { relationship ->
    relationship.relationshipType == relationshipType
}.map { relationship ->
    Artist.Relationship(
        id = relationship.childId,
        name = relationship.name,
        resourceUrl = relationship.resourceUrl,
        active = relationship.active,
        thumbnailUrl = relationship.thumbnailUrl
    )
}

// endregion Methods
