package com.codepunk.skeleton.data.localv2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.codepunk.skeleton.data.localv2.entity.LocalArtist
import com.codepunk.skeleton.data.localv2.entity.LocalArtistReference
import com.codepunk.skeleton.data.localv2.entity.LocalCreditReference
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalLabel
import com.codepunk.skeleton.data.localv2.entity.LocalLabelReference
import com.codepunk.skeleton.data.localv2.entity.LocalMaster
import com.codepunk.skeleton.data.localv2.entity.LocalResource
import com.codepunk.skeleton.data.localv2.entity.LocalResourceDetail
import com.codepunk.skeleton.data.localv2.entity.LocalTrack
import com.codepunk.skeleton.data.localv2.entity.LocalVideo
import com.codepunk.skeleton.data.localv2.relation.LocalArtistArtistReferenceCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalLabelLabelReferenceCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndMaster
import com.codepunk.skeleton.data.localv2.relation.LocalResourceCreditReferenceCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalResourceImageCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalResourceTrackCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalResourceVideoCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalTrackCreditReferenceCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalTrackWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DiscogsDao {

    // region Methods

    // ====================
    // Resource
    // ====================

    @Insert
    abstract suspend fun insertResource(resource: LocalResource): Long

    @Upsert
    abstract suspend fun upsertResource(resource: LocalResource): Long

    // ====================
    // Resource detail
    // ====================

    @Insert
    abstract suspend fun insertResourceDetails(details: List<LocalResourceDetail>): List<Long>

    // ====================
    // Credit reference
    // ====================

    @Insert
    abstract suspend fun insertCreditReferences(creditRefs: List<LocalCreditReference>): List<Long>

    @Insert
    abstract suspend fun insertResourceCreditReferenceCrossRefs(
        crossRefs: List<LocalResourceCreditReferenceCrossRef>
    ): List<Long>

    @Insert
    abstract suspend fun insertTrackCreditReferenceCrossRefs(
        crossRefs: List<LocalTrackCreditReferenceCrossRef>
    ): List<Long>

    private suspend fun insertResourceCreditReferences(
        resourceId: Long,
        creditRefs: List<LocalCreditReference>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val referenceIds = insertCreditReferences(creditRefs)
        val crossRefs = referenceIds
            .filter { it != -1L }
            .mapIndexed { index, referenceId ->
                LocalResourceCreditReferenceCrossRef(resourceId, referenceId, index)
            }
        insertResourceCreditReferenceCrossRefs(crossRefs)
        return referenceIds
    }

    @Transaction
    @Query("")
    private suspend fun insertTrackCreditReferences(
        trackId: Long,
        creditRefs: List<LocalCreditReference>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val referenceIds = insertCreditReferences(creditRefs)
        val crossRefs = referenceIds
            .filter { it != -1L }
            .mapIndexed { index, referenceId ->
                LocalTrackCreditReferenceCrossRef(trackId, referenceId, index)
            }
        insertTrackCreditReferenceCrossRefs(crossRefs)
        return referenceIds
    }


    // ====================
    // Image
    // ====================

    @Insert
    abstract suspend fun insertImage(image: LocalImage): Long

    @Insert
    abstract suspend fun insertImages(images: List<LocalImage>): List<Long>

    @Insert
    abstract suspend fun insertResourceImageCrossRefs(
        crossRefs: List<LocalResourceImageCrossRef>
    ): List<Long>

    private suspend fun insertResourceImages(
        resourceId: Long,
        images: List<LocalImage>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val imageIds = insertImages(images)
        val crossRefs = imageIds
            .filter { it != -1L }
            .mapIndexed { index, imageId ->
                LocalResourceImageCrossRef(resourceId, imageId, index)
            }
        insertResourceImageCrossRefs(crossRefs)
        return imageIds
    }

    // ====================
    // Artist
    // ====================

    @Insert
    abstract suspend fun insertArtist(artist: LocalArtist): Long

    @Upsert
    abstract suspend fun upsertArtist(artist: LocalArtist): Long

    @Transaction
    @Query("")
    suspend fun insertResourceAndArtist(resourceAndArtist: LocalResourceAndArtist): Long {
        val resourceId = upsertResource(resourceAndArtist.resource)
        if (resourceId != -1L) {
            with(resourceAndArtist.artistWithDetails) {
                val artistId = upsertArtist(artist.copy(resourceId = resourceId))
                if (artistId != -1L) {
                    insertResourceImages(resourceId, images)
                    insertResourceDetails(details.map { it.copy(resourceId = resourceId) })
                    insertArtistArtistReferences(artistId, artistRefs)
                }
            }
        }
        return resourceId
    }

    /*
     * Old version: "SELECT * FROM resource WHERE resource_id = (SELECT resource_id FROM ARTIST WHERE artist_id = :artistId)"
     */
    @Query("""
        SELECT *
          FROM resource
          LEFT OUTER JOIN artist
            ON resource.resource_id = artist.resource_id
         WHERE artist.artist_id = :artistId
    """)
    abstract fun getResourceAndArtist(artistId: Long): Flow<LocalResourceAndArtist?>

    // ====================
    // Artist reference
    // ====================

    @Insert
    abstract suspend fun insertArtistReferences(artistRefs: List<LocalArtistReference>): List<Long>

    @Insert
    abstract suspend fun insertArtistArtistReferenceCrossRefs(
        crossRefs: List<LocalArtistArtistReferenceCrossRef>
    ): List<Long>

    @Transaction
    @Query("")
    private suspend fun insertArtistArtistReferences(
        artistId: Long,
        artistRefs: List<LocalArtistReference>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val referenceIds = insertArtistReferences(artistRefs)
        val crossRefs = referenceIds
            .filter { it != -1L }
            .mapIndexed { index, referenceId ->
                LocalArtistArtistReferenceCrossRef(artistId, referenceId, index)
            }
        insertArtistArtistReferenceCrossRefs(crossRefs)
        return referenceIds
    }

    // ====================
    // Label
    // ====================

    @Insert
    abstract suspend fun insertLabel(label: LocalLabel): Long

    @Upsert
    abstract suspend fun upsertLabel(label: LocalLabel): Long

    @Transaction
    @Query("")
    suspend fun insertResourceAndLabel(resourceAndLabel: LocalResourceAndLabel): Long {
        val resourceId = upsertResource(resourceAndLabel.resource)
        if (resourceId != -1L) {
            with(resourceAndLabel.labelWithDetails) {
                val updatedLabel = label.copy(resourceId = resourceId)
                val labelId = upsertLabel(updatedLabel)
                if (labelId != -1L) {
                    insertResourceImages(resourceId, images)
                    insertResourceDetails(details.map { it.copy(resourceId = resourceId) })
                    insertLabelLabelReferences(labelId, labelRefs)
                }
            }
        }
        return resourceId
    }

    @Query("""
        SELECT *
          FROM resource
          LEFT OUTER JOIN label
            ON resource.resource_id = label.resource_id
         WHERE label.label_id = :labelId
    """)
    abstract fun getResourceAndLabel(labelId: Long): Flow<LocalResourceAndLabel?>

    // ====================
    // Label reference
    // ====================

    @Insert
    abstract suspend fun insertLabelReferences(labelRefs: List<LocalLabelReference>): List<Long>

    @Insert
    abstract suspend fun insertLabelLabelReferenceCrossRefs(
        crossRefs: List<LocalLabelLabelReferenceCrossRef>
    ): List<Long>

    private suspend fun insertLabelLabelReferences(
        labelId: Long,
        labelRefs: List<LocalLabelReference>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val referenceIds = insertLabelReferences(labelRefs)
        val crossRefs = referenceIds
            .filter { it != -1L }
            .mapIndexed { index, referenceId ->
                LocalLabelLabelReferenceCrossRef(labelId, referenceId, index)
            }
        insertLabelLabelReferenceCrossRefs(crossRefs)
        return referenceIds
    }

    // ====================
    // Master
    // ====================

    @Insert
    abstract suspend fun insertMaster(master: LocalMaster): Long

    @Upsert
    abstract suspend fun upsertMaster(master: LocalMaster): Long

    @Transaction
    @Query("")
    suspend fun insertResourceAndMaster(resourceAndMaster: LocalResourceAndMaster): Long {
        val resourceId = upsertResource(resourceAndMaster.resource)
        if (resourceId != -1L) {
            with(resourceAndMaster.masterWithDetails) {
                val updatedMaster = master.copy(resourceId = resourceId)
                val masterId = upsertMaster(updatedMaster)
                if (masterId != -1L) {
                    insertResourceImages(resourceId, images)
                    insertResourceDetails(details.map { it.copy(resourceId = resourceId) })
                    insertResourceTracksWithDetails(resourceId, trackList)
                    insertResourceCreditReferences(resourceId, artists)
                    insertResourceVideoReferences(resourceId, videos)
                }
            }
        }
        return resourceId
    }
    @Query("""
        SELECT *
          FROM resource
          LEFT OUTER JOIN master
            ON resource.resource_id = master.resource_id
         WHERE master.master_id = :masterId
    """)
    abstract fun getResourceAndMaster(masterId: Long): Flow<LocalResourceAndMaster?>

    // ====================
    // Track
    // ====================

    @Insert
    abstract suspend fun insertTrack(track: LocalTrack): Long

    @Insert
    abstract suspend fun insertTracks(tracks: List<LocalTrack>): List<Long>

    @Insert
    abstract suspend fun insertResourceTrackCrossRefs(
        crossRefs: List<LocalResourceTrackCrossRef>
    ): List<Long>

    @Transaction
    @Query("")
    suspend fun insertResourceTrackWithDetails(
        resourceId: Long?,
        trackWithDetails: LocalTrackWithDetails
    ): Long {
        val trackId = insertTrack(trackWithDetails.track)
        trackWithDetails.extraArtists?.also { trackCreditReferences ->
            insertTrackCreditReferences(trackId, trackCreditReferences)
        }
        return trackId
    }

    @Transaction
    @Query("")
    suspend fun insertResourceTracksWithDetails(
        resourceId: Long,
        tracksWithDetails: List<LocalTrackWithDetails>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val trackIds = tracksWithDetails.map { trackWithDetails ->
            insertResourceTrackWithDetails(resourceId, trackWithDetails)
        }
        val crossRefs = trackIds
            .filter { it != -1L }
            .mapIndexed { index, trackId ->
                LocalResourceTrackCrossRef(resourceId, trackId, index)
            }
        insertResourceTrackCrossRefs(crossRefs)
        return trackIds
    }

    private suspend fun insertTracksWithDetails(
        trackWithDetails: List<LocalTrackWithDetails>
    ): List<Long> {
        TODO("")
    }

    @Transaction
    @Query("")
    suspend fun insertTrackWithDetails(
        trackWithDetails: LocalTrackWithDetails
    ): Long {
        TODO("")
    }

    @Query("""
        SELECT track.* 
        FROM resource_track_cross_ref, track 
        WHERE resource_track_cross_ref.track_id = track.track_id
        AND resource_track_cross_ref.resource_id = :resourceId
    """)
    abstract suspend fun getTracksWithDetails(resourceId: Long): List<LocalTrackWithDetails>

    // ====================
    // Video
    // ====================

    @Insert
    abstract suspend fun insertVideos(videos: List<LocalVideo>): List<Long>

    @Insert
    abstract suspend fun insertResourceVideoCrossRefs(
        crossRefs: List<LocalResourceVideoCrossRef>
    ): List<Long>

    private suspend fun insertResourceVideoReferences(
        resourceId: Long,
        videos: List<LocalVideo>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val videoIds = insertVideos(videos)
        val crossRefs = videoIds
            .filter { it != -1L }
            .mapIndexed { index, videoId ->
                LocalResourceVideoCrossRef(resourceId, videoId, index)
            }
        insertResourceVideoCrossRefs(crossRefs)
        return videoIds
    }

    // endregion Methods

}