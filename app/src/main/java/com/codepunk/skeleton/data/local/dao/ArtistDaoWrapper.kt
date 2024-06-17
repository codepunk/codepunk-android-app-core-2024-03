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
    private val artistDao: ArtistDao,
    private val imageDao: ImageDao
) : ArtistDao() {

    // region Implemented methods

    override suspend fun insertArtist(artist: LocalArtist): Long =
        artistDao.insertArtist(artist)

    override suspend fun insertArtists(artists: List<LocalArtist>): List<Long> =
        artistDao.insertArtists(artists)

    override suspend fun insertArtistImageCrossRef(crossRef: LocalArtistImageCrossRef) {
        artistDao.insertArtistImageCrossRef(crossRef)
    }

    override suspend fun insertArtistImageCrossRefs(crossRefs: List<LocalArtistImageCrossRef>) {
        artistDao.insertArtistImageCrossRefs(crossRefs)
    }

    override suspend fun insertArtistDetail(detail: LocalArtistDetail) {
        artistDao.insertArtistDetail(detail)
    }

    override suspend fun insertArtistDetails(details: List<LocalArtistDetail>) {
        artistDao.insertArtistDetails(details)
    }

    override suspend fun insertArtistRelationship(relationship: LocalArtistRelationship) {
        artistDao.insertArtistRelationship(relationship)
    }

    override suspend fun insertArtistRelationships(relationships: List<LocalArtistRelationship>) {
        artistDao.insertArtistRelationships(relationships)
    }

    override fun getArtistWithDetails(id: Long): Flow<LocalArtistWithDetails?> =
        artistDao.getArtistWithDetails(id)

    // endregion Implemented methods

    // region Methods

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override suspend fun insertArtistWithDetails(
        artistWithDetails: LocalArtistWithDetails
    ): Long {
        val artistId = artistDao.insertArtist(artistWithDetails.artist)
        if (artistId != -1L) {
            val crossRefs = imageDao.insertImages(artistWithDetails.images)
                .filter { it != -1L }
                .map { LocalArtistImageCrossRef(artistId = artistId, imageId = it) }
            artistDao.insertArtistImageCrossRefs(crossRefs)
            artistDao.insertArtistDetails(artistWithDetails.details)
            artistDao.insertArtistRelationships(artistWithDetails.relationships)
        }
        return artistId
    }

    // endregion Methods

}