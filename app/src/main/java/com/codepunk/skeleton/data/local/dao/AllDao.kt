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
    suspend fun deleteArtist(artistId: Long): Boolean =
        resourceDao.getResourceByArtistId(artistId)?.apply {
            imageDao.deleteResourceImages(resourceId)
            resourceDao.deleteResource(this)
        } != null

    @Transaction
    @Query("")
    suspend fun insertArtist(resourceAndArtist: LocalResourceAndArtist): Long {
        deleteArtist(resourceAndArtist.artistWithDetails.artist.artistId)
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
    suspend fun deleteLabel(labelId: Long): Boolean =
        resourceDao.getResourceByLabelId(labelId)?.apply {
            imageDao.deleteResourceImages(resourceId)
            resourceDao.deleteResource(this)
        } != null

    @Transaction
    @Query("")
    suspend fun insertLabel(resourceAndLabel: LocalResourceAndLabel): Long {
        deleteLabel(resourceAndLabel.labelWithDetails.label.labelId)
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
    suspend fun deleteMaster(masterId: Long): Boolean =
        resourceDao.getResourceByMasterId(masterId)?.apply {
            imageDao.deleteResourceImages(resourceId)
            creditDao.deleteResourceCredits(resourceId)
            creditDao.deleteTrackCreditsByResource(resourceId)
            trackDao.deleteResourceTracks(resourceId)
            videoDao.deleteResourceVideos(resourceId)
            resourceDao.deleteResource(this)
        } != null

    @Transaction
    @Query("")
    suspend fun insertMaster(resourceAndMaster: LocalResourceAndMaster): Long {
        deleteMaster(resourceAndMaster.masterWithDetails.master.masterId)
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
    suspend fun deleteRelease(releaseId: Long): Boolean =
        resourceDao.getResourceByReleaseId(releaseId)?.apply {
            imageDao.deleteResourceImages(resourceId)
            creditDao.deleteResourceCredits(resourceId)
            creditDao.deleteTrackCreditsByResource(resourceId)
            trackDao.deleteResourceTracks(resourceId)
            videoDao.deleteResourceVideos(resourceId)
            resourceDao.deleteResource(this)
        } != null

    @Transaction
    @Query("")
    suspend fun insertRelease(resourceAndRelease: LocalResourceAndRelease): Long {
        deleteRelease(resourceAndRelease.releaseWithDetails.release.releaseId)
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
