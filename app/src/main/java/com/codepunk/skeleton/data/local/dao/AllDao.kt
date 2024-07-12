package com.codepunk.skeleton.data.local.dao

import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.relation.LocalFormatWithDetails
import com.codepunk.skeleton.data.local.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.local.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.local.relation.LocalResourceAndMaster
import com.codepunk.skeleton.data.local.relation.LocalResourceAndRelease
import com.codepunk.skeleton.data.local.relation.LocalResourceTrackCrossRef
import com.codepunk.skeleton.data.local.relation.LocalTrackWithDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AllDao @Inject constructor(
    private val artistDao: ArtistDao,
    private val creditDao: CreditDao,
    private val formatDao: FormatDao,
    private val identifierDao: IdentifierDao,
    private val imageDao: ImageDao,
    private val labelDao: LabelDao,
    private val masterDao: MasterDao,
    private val relatedArtistDao: RelatedArtistDao,
    private val relatedLabelDao: RelatedLabelDao,
    private val releaseDao: ReleaseDao,
    private val resourceDao: ResourceDao,
    private val trackDao: TrackDao,
    private val videoDao: VideoDao
) {

    // region Methods

    // ====================
    // Artist
    // ====================

    @Transaction
    @Query("")
    suspend fun deleteResourceAndArtist(artistId: Long): Boolean =
        withContext(Dispatchers.IO) {
            resourceDao.getResourceByArtistId(artistId)?.apply {
                resourceDao.deleteResource(this)
                imageDao.scrubImages()
            } != null
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndArtist(resourceAndArtist: LocalResourceAndArtist): Long {
        deleteResourceAndArtist(resourceAndArtist.artistWithDetails.artist.artistId)
        val resourceId = resourceDao.insertResource(resourceAndArtist.resource)
        with(resourceAndArtist.artistWithDetails) {
            artistDao.insertArtist(artist.copy(resourceId = resourceId))
            imageDao.insertResourceImages(resourceId, images)
            resourceDao.insertResourceDetails(resourceId, details)
            relatedArtistDao.insertRelatedArtists(resourceId, relatedArtists)
        }
        return resourceId
    }

    // ====================
    // Label
    // ====================

    @Transaction
    @Query("")
    suspend fun deleteResourceAndLabel(labelId: Long): Boolean =
        withContext(Dispatchers.IO) {
            resourceDao.getResourceByLabelId(labelId)?.apply {
                resourceDao.deleteResource(this)
                imageDao.scrubImages()
            } != null
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndLabel(resourceAndLabel: LocalResourceAndLabel): Long {
        deleteResourceAndLabel(resourceAndLabel.labelWithDetails.label.labelId)
        val resourceId = resourceDao.insertResource(resourceAndLabel.resource)
        with(resourceAndLabel.labelWithDetails) {
            labelDao.insertLabel(label.copy(resourceId = resourceId))
            imageDao.insertResourceImages(resourceId, images)
            resourceDao.insertResourceDetails(resourceId, details)
            relatedLabelDao.insertRelatedLabels(resourceId, relatedLabels)
        }
        return resourceId
    }

    // ====================
    // Master
    // ====================

    @Transaction
    @Query("")
    suspend fun deleteResourceAndMaster(masterId: Long): Boolean =
        withContext(Dispatchers.IO) {
            resourceDao.getResourceByMasterId(masterId).apply {
                val x = "$this"
            }?.apply {
                val result = resourceDao.deleteResource(this)
                val x = "$result"
                imageDao.scrubImages()
                trackDao.scrubTracks()
                creditDao.scrubCredits()
            } != null
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndMaster(resourceAndMaster: LocalResourceAndMaster): Long {
        deleteResourceAndMaster(resourceAndMaster.masterWithDetails.master.masterId)
        val resourceId = resourceDao.insertResource(resourceAndMaster.resource)
        with(resourceAndMaster.masterWithDetails) {
            masterDao.insertMaster(master.copy(resourceId = resourceId))
            imageDao.insertResourceImages(resourceId, images)
            resourceDao.insertResourceDetails(resourceId, details)
            insertResourceTracksWithDetails(resourceId, trackList)
            creditDao.insertResourceCredits(resourceId, credits)
            videoDao.insertResourceVideos(resourceId, videos)
        }
        return resourceId
    }

    // ====================
    // Release
    // ====================

    @Transaction
    @Query("")
    suspend fun deleteResourceAndRelease(releaseId: Long): Boolean =
        withContext(Dispatchers.IO) {
            resourceDao.getResourceByReleaseId(releaseId)?.apply {
                resourceDao.deleteResource(this)
                imageDao.scrubImages()
                trackDao.scrubTracks()
                creditDao.scrubCredits()
            } != null
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndRelease(resourceAndRelease: LocalResourceAndRelease): Long {
        deleteResourceAndRelease(resourceAndRelease.releaseWithDetails.release.releaseId)
        val resourceId = resourceDao.insertResource(resourceAndRelease.resource)
        with(resourceAndRelease.releaseWithDetails) {
            val releaseId = releaseDao.insertRelease(release.copy(resourceId = resourceId))
            imageDao.insertResourceImages(resourceId, images)
            resourceDao.insertResourceDetails(resourceId, details)
            insertResourceTracksWithDetails(resourceId, trackList)
            creditDao.insertResourceCredits(resourceId, credits)
            videoDao.insertResourceVideos(resourceId, videos)
            relatedLabelDao.insertRelatedLabels(resourceId, relatedLabels)
            insertFormatsWithDetails(releaseId, formats)
            identifierDao.insertIdentifiers(releaseId, identifiers)
        }
        return resourceId
    }

    // ====================
    // Format
    // ====================

    @Transaction
    @Query("")
    suspend fun insertFormatsWithDetails(
        releaseId: Long,
        formatsWithDetails: List<LocalFormatWithDetails>
    ): List<Long> = formatDao.insertFormats(
        formatsWithDetails.map { it.format.copy(releaseId = releaseId) }
    ).also { formatIds ->
        formatIds.zip(formatsWithDetails).forEach { (formatId, formatWithDetails) ->
            formatDao.insertFormatDetails(formatId, formatWithDetails.details)
        }
    }

    // ====================
    // Track
    // ====================

    @Transaction
    @Query("")
    suspend fun insertTracksWithDetails(
        tracksWithDetails: List<LocalTrackWithDetails>
    ): List<Long> = trackDao.insertTracks(
        tracksWithDetails.map { it.track }
    ).also { trackIds ->
        trackIds.zip(tracksWithDetails).forEach { (trackId, trackWithDetails) ->
            trackWithDetails.extraArtists?.run {
               creditDao.insertTrackCredits(trackId, this)
            }
        }
    }

    @Transaction
    @Query("")
    suspend fun insertResourceTracksWithDetails(
        resourceId: Long,
        tracksWithDetails: List<LocalTrackWithDetails>
    ): List<Long> = insertTracksWithDetails(tracksWithDetails).apply {
        filter { it != -1L }
            .map { LocalResourceTrackCrossRef(resourceId, it) }
            .run { trackDao.insertResourceTrackCrossRefs(this) }
    }

    // endregion Methods

}
