package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalRelatedArtist
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDetail
import com.codepunk.skeleton.data.local.entity.LocalIdentifier
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.entity.LocalLabel
import com.codepunk.skeleton.data.local.entity.LocalRelatedLabel
import com.codepunk.skeleton.data.local.entity.LocalMaster
import com.codepunk.skeleton.data.local.entity.LocalRelease
import com.codepunk.skeleton.data.local.entity.LocalResource
import com.codepunk.skeleton.data.local.entity.LocalResourceDetail
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.entity.LocalVideo
import com.codepunk.skeleton.data.local.relation.LocalFormatWithDetails
import com.codepunk.skeleton.data.local.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.local.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.local.relation.LocalResourceAndMaster
import com.codepunk.skeleton.data.local.relation.LocalResourceAndRelease
import com.codepunk.skeleton.data.local.relation.LocalResourceCreditCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceTrackCrossRef
import com.codepunk.skeleton.data.local.relation.LocalResourceVideoCrossRef
import com.codepunk.skeleton.data.local.relation.LocalTrackCreditCrossRef
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
    // Credit
    // ====================

    @Insert
    abstract suspend fun insertCredits(credits: List<LocalCredit>): List<Long>

    @Insert
    abstract suspend fun insertResourceCreditCrossRefs(
        crossRefs: List<LocalResourceCreditCrossRef>
    ): List<Long>

    @Insert
    abstract suspend fun insertTrackCreditCrossRefs(
        crossRefs: List<LocalTrackCreditCrossRef>
    ): List<Long>

    private suspend fun insertResourceCredits(
        resourceId: Long,
        credits: List<LocalCredit>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val referenceIds = insertCredits(credits)
        val crossRefs = referenceIds
            .filter { it != -1L }
            .map { referenceId -> LocalResourceCreditCrossRef(resourceId, referenceId) }
        insertResourceCreditCrossRefs(crossRefs)
        return referenceIds
    }

    @Transaction
    @Query("")
    private suspend fun insertTrackCredits(
        trackId: Long,
        credits: List<LocalCredit>
    ): List<Long> {
        // TODO Insert or Upsert? Clean beforehand?
        val referenceIds = insertCredits(credits)
        val crossRefs = referenceIds
            .filter { it != -1L }
            .map { referenceId -> LocalTrackCreditCrossRef(trackId, referenceId) }
        insertTrackCreditCrossRefs(crossRefs)
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
            .map { imageId -> LocalResourceImageCrossRef(resourceId, imageId) }
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
                    insertRelatedArtists(relatedArtists.map { it.copy(resourceId = resourceId) })
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
    // Related artist
    // ====================

    @Insert
    abstract suspend fun insertRelatedArtists(relatedArtists: List<LocalRelatedArtist>): List<Long>

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
                    val mapped = relatedLabels.map { it.copy(resourceId = resourceId) }
                    insertRelatedLabels(mapped)
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
    // Related label
    // ====================

    @Insert
    abstract suspend fun insertRelatedLabels(relatedLabels: List<LocalRelatedLabel>): List<Long>

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
                    insertResourceCredits(resourceId, credits)
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
                    insertResourceCredits(resourceId, relatedArtists)
                    insertResourceVideoReferences(resourceId, videos)
                    insertRelatedLabels(relatedLabels.map { it.copy(resourceId = resourceId) })
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
        trackWithDetails.extraArtists?.also { insertTrackCredits(trackId, it) }
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
            .map { trackId -> LocalResourceTrackCrossRef(resourceId, trackId) }
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
        formatWithDescriptions: LocalFormatWithDetails
    ) {
        val formatId = insertReleaseFormat(
            formatWithDescriptions.format.copy(releaseId = releaseId)
        )
        insertReleaseFormatDescriptions(
            formatWithDescriptions.details.map {
                it.copy(formatId = formatId)
            }
        )
    }

    @Insert
    abstract suspend fun insertReleaseFormatDescriptions(
        descriptions: List<LocalFormatDetail>
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
            .map { videoId -> LocalResourceVideoCrossRef(resourceId, videoId) }
        insertResourceVideoCrossRefs(crossRefs)
        return videoIds
    }

    // endregion Methods

}