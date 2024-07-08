package com.codepunk.skeleton.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.relation.LocalArtistAliasCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistGroupCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistMemberCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistRelationshipCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistWithDetails
import kotlinx.coroutines.flow.Flow

class ArtistDaoWrapper(
    private val wrapped: ArtistDao,
    private val imageDao: ImageDao
) : ArtistDao() {

    // region Implemented methods

    override suspend fun insertArtist(artist: LocalArtist): Long =
        wrapped.insertArtist(artist)

    override suspend fun insertArtists(artists: List<LocalArtist>): List<Long> =
        wrapped.insertArtists(artists)

    override suspend fun insertArtistImageCrossRef(crossRef: LocalArtistImageCrossRef) {
        wrapped.insertArtistImageCrossRef(crossRef)
    }

    override suspend fun insertArtistImageCrossRefs(crossRefs: List<LocalArtistImageCrossRef>) {
        wrapped.insertArtistImageCrossRefs(crossRefs)
    }

    override suspend fun insertArtistDetail(detail: LocalArtistDetail) {
        wrapped.insertArtistDetail(detail)
    }

    override suspend fun insertArtistDetails(details: List<LocalArtistDetail>) {
        wrapped.insertArtistDetails(details)
    }

    override suspend fun insertArtistRelationship(relationship: LocalArtistRelationship): Long =
        wrapped.insertArtistRelationship(relationship)

    override suspend fun insertArtistRelationships(
        relationships: List<LocalArtistRelationship>
    ): List<Long> = wrapped.insertArtistRelationships(relationships)

    override suspend fun insertArtistAliasCrossRefs(crossRefs: List<LocalArtistAliasCrossRef>) {
        wrapped.insertArtistAliasCrossRefs(crossRefs)
    }

    override suspend fun insertArtistGroupCrossRefs(crossRefs: List<LocalArtistGroupCrossRef>) {
        wrapped.insertArtistGroupCrossRefs(crossRefs)
    }

    override suspend fun insertArtistMemberCrossRefs(crossRefs: List<LocalArtistMemberCrossRef>) {
        wrapped.insertArtistMemberCrossRefs(crossRefs)
    }

    override fun getArtistWithDetails(artistId: Long): Flow<LocalArtistWithDetails?> =
        wrapped.getArtistWithDetails(artistId)

    // endregion Implemented methods

    // region Methods

    private suspend fun <T : LocalArtistRelationshipCrossRef> insertAndMapArtistRelationships(
        relationships: List<LocalArtistRelationship>,
        transform: (index: Int, id: Long) -> T
    ): List<T> = wrapped.insertArtistRelationships(relationships)
        .filter { it != -1L } // TODO Might not need this with upsert
        .mapIndexed { index, id -> transform(index, id) }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override suspend fun insertArtistWithDetails(
        artistWithDetails: LocalArtistWithDetails
    ): Long {
        val artistId = wrapped.insertArtist(artistWithDetails.artist)
        if (artistId != -1L) { // TODO Might not need this with upsert
            val imageCrossRefs = imageDao.insertImages(artistWithDetails.images)
                .filter { it != -1L } // TODO Might not need this with upsert
                .map { LocalArtistImageCrossRef(artistId = artistId, imageId = it) }
            wrapped.insertArtistImageCrossRefs(imageCrossRefs)
            wrapped.insertArtistDetails(artistWithDetails.details)
            val aliasCrossRefs = insertAndMapArtistRelationships(
                artistWithDetails.aliases
            ) { index, id -> LocalArtistAliasCrossRef(artistId, id, index) }
            wrapped.insertArtistAliasCrossRefs(aliasCrossRefs)
            val memberCrossRefs = insertAndMapArtistRelationships(
                artistWithDetails.members
            ) { index, id -> LocalArtistMemberCrossRef(artistId, id, index) }
            wrapped.insertArtistMemberCrossRefs(memberCrossRefs)
            val groupCrossRefs = insertAndMapArtistRelationships(
                artistWithDetails.groups
            ) { index, id -> LocalArtistGroupCrossRef(artistId, id, index) }
            wrapped.insertArtistGroupCrossRefs(groupCrossRefs)
        }
        return artistId
    }

    // endregion Methods

}
