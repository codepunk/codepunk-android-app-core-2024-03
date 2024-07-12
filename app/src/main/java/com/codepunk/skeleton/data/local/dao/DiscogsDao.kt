package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistReference
import com.codepunk.skeleton.data.local.entity.LocalCreditReference
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDescription
import com.codepunk.skeleton.data.local.entity.LocalIdentifier
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalLabelReference
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalResource
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.entity.LocalVideo
import com.codepunk.skeleton.data.local.relation.LocalFormatWithDescriptions
import com.codepunk.skeleton.data.local.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.local.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.local.relation.LocalResourceAndMaster
import com.codepunk.skeleton.data.local.relation.LocalResourceAndRelease
import com.codepunk.skeleton.data.local.relation.LocalResourceCreditReferenceCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceTrackCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceVideoCrossRef
import com.codepunk.skeleton.data.local.relation.LocalTrackCreditReferenceCrossRef
import com.codepunk.skeleton.data.local.relation.LocalTrackWithDetails
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
                    insertArtistReferences(artistRefs.map { it.copy(resourceId = resourceId) })
                }
            }
        }
        return resourceId
    }

    @Transaction
    @Query("""
        SELECT resource.*
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
                val labelId = upsertLabel(label.copy(resourceId = resourceId))
                if (labelId != -1L) {
                    insertResourceImages(resourceId, images)
                    insertResourceDetails(details.map { it.copy(resourceId = resourceId) })
                    val mapped = labelRefs.map { it.copy(resourceId = resourceId) }
                    insertLabelReferences(mapped)
                }
            }
        }
        return resourceId
    }

    @Transaction
    @Query("""
        SELECT resource.*
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

    @Transaction
    @Query("""
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN master
            ON resource.resource_id = master.resource_id
         WHERE master.master_id = :masterId
    """)
    abstract fun getResourceAndMaster(masterId: Long): Flow<LocalResourceAndMaster?>

    // ====================
    // Release
    // ====================

    @Insert
    abstract suspend fun insertRelease(release: LocalRelease): Long

    @Upsert
    abstract suspend fun upsertRelease(release: LocalRelease): Long

    @Transaction
    @Query("")
    suspend fun insertResourceAndRelease(resourceAndRelease: LocalResourceAndRelease): Long {
        val resourceId = upsertResource(resourceAndRelease.resource)
        if (resourceId != -1L) {
            with(resourceAndRelease.releaseWithDetails) {
                val updatedRelease = release.copy(resourceId = resourceId)
                val releaseId = upsertRelease(updatedRelease)
                if (releaseId != -1L) {
                    insertResourceImages(resourceId, images)
                    insertResourceDetails(details.map { it.copy(resourceId = resourceId) })
                    insertResourceTracksWithDetails(resourceId, trackList)
                    insertResourceCreditReferences(resourceId, relatedArtists)
                    insertResourceVideoReferences(resourceId, videos)
                    insertLabelReferences(labelRefs.map { it.copy(resourceId = resourceId) })
                    formats.forEach { insertReleaseFormatWithDescription(releaseId, it) }
                    insertReleaseIdentifiers(identifiers.map { it.copy(releaseId = releaseId) })
                }
            }
        }
        return resourceId
    }

    @Transaction
    @Query("""
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN `release`
            ON resource.resource_id = `release`.resource_id
         WHERE `release`.release_id = :releaseId
    """)
    abstract fun getResourceAndRelease(releaseId: Long): Flow<LocalResourceAndRelease?>

    // ====================
    // Track
    // ====================

    @Insert
    abstract suspend fun insertTrack(track: LocalTrack): Long

    @Insert
    abstract suspend fun insertResourceTrackCrossRefs(
        crossRefs: List<LocalResourceTrackCrossRef>
    ): List<Long>

    @Transaction
    @Query("")
    suspend fun insertResourceTrackWithDetails(
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
            insertResourceTrackWithDetails(trackWithDetails)
        }
        val crossRefs = trackIds
            .filter { it != -1L }
            .mapIndexed { index, trackId ->
                LocalResourceTrackCrossRef(resourceId, trackId, index)
            }
        insertResourceTrackCrossRefs(crossRefs)
        return trackIds
    }

    // ====================
    // Identifier
    // ====================

    @Insert
    abstract suspend fun insertReleaseIdentifiers(identifiers: List<LocalIdentifier>): List<Long>

    // ====================
    // Format
    // ====================

    @Insert
    abstract suspend fun insertReleaseFormat(format: LocalFormat): Long

    @Transaction
    @Query("")
    suspend fun insertReleaseFormatWithDescription(
        releaseId: Long,
        formatWithDescriptions: LocalFormatWithDescriptions
    ) {
        val formatId = insertReleaseFormat(
            formatWithDescriptions.format.copy(releaseId = releaseId)
        )
        insertReleaseFormatDescriptions(
            formatWithDescriptions.descriptions.map {
                it.copy(formatId = formatId)
            }
        )
    }

    @Insert
    abstract suspend fun insertReleaseFormatDescriptions(
        descriptions: List<LocalFormatDescription>
    )

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