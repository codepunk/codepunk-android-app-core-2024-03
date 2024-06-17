package com.codepunk.skeleton.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.relation.LocalArtistImageCrossRef
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

    override suspend fun insertArtistRelationship(relationship: LocalArtistRelationship) {
        wrapped.insertArtistRelationship(relationship)
    }

    override suspend fun insertArtistRelationships(relationships: List<LocalArtistRelationship>) {
        wrapped.insertArtistRelationships(relationships)
    }

    override fun getArtistWithDetails(id: Long): Flow<LocalArtistWithDetails?> =
        wrapped.getArtistWithDetails(id)

    // endregion Implemented methods

    // region Methods

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override suspend fun insertArtistWithDetails(
        artistWithDetails: LocalArtistWithDetails
    ): Long {
        val artistId = wrapped.insertArtist(artistWithDetails.artist)
        if (artistId != -1L) {
            val crossRefs = imageDao.insertImages(artistWithDetails.images)
                .filter { it != -1L }
                .map { LocalArtistImageCrossRef(artistId = artistId, imageId = it) }
            wrapped.insertArtistImageCrossRefs(crossRefs)
            wrapped.insertArtistDetails(artistWithDetails.details)
            wrapped.insertArtistRelationships(artistWithDetails.relationships)
        }
        return artistId
    }

    // endregion Methods

}