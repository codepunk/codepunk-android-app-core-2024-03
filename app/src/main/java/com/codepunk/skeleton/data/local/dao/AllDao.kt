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

// TODO When deleting resource, need to cleanly delete all related images
// (And similarly any other cross-refs

class AllDao @Inject constructor(
    val artistDao: ArtistDao,
    val creditDao: CreditDao,
    val formatDao: FormatDao,
    val identifierDao: IdentifierDao,
    val imageDao: ImageDao,
    val labelDao: LabelDao,
    val masterDao: MasterDao,
    val relatedArtistDao: RelatedArtistDao,
    val relatedLabelDao: RelatedLabelDao,
    val releaseDao: ReleaseDao,
    val resourceDao: ResourceDao,
    val trackDao: TrackDao,
    val videoDao: VideoDao
) {

    // region Methods

    // ====================
    // Artist
    // ====================

    @Transaction
    @Query("")
    suspend fun deleteResourceAndArtist(resourceAndArtist: LocalResourceAndArtist): Boolean =
        withContext(Dispatchers.IO) {
            resourceDao.getResourceByArtistId(
                resourceAndArtist.artistWithDetails.artist.artistId
            )?.let {
                resourceDao.deleteResource(it)
                imageDao.cleanImages()
                true
            } ?: false
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndArtist(resourceAndArtist: LocalResourceAndArtist): Long {
        deleteResourceAndArtist(resourceAndArtist)
        val resourceId = resourceDao.insertResource(resourceAndArtist.resource)
        with(resourceAndArtist.artistWithDetails) {
            artistDao.insertArtist(artist.copy(resourceId = resourceId))
            imageDao.insertResourceImages(resourceId, images)
            resourceDao.insertResourceDetails(details.map { it.copy(resourceId = resourceId) })
            relatedArtistDao.insertRelatedArtists(
                relatedArtists.map { it.copy(resourceId = resourceId) }
            )
        }
        return resourceId
    }

    // ====================
    // Label
    // ====================

    @Transaction
    @Query("")
    suspend fun deleteResourceAndLabel(resourceAndLabel: LocalResourceAndLabel): Boolean =
        withContext(Dispatchers.IO) {
            resourceDao.getResourceByLabelId(
                resourceAndLabel.labelWithDetails.label.labelId
            )?.let {
                resourceDao.deleteResource(it)
                imageDao.cleanImages()
                true
            } ?: false
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndLabel(resourceAndLabel: LocalResourceAndLabel): Long {
        deleteResourceAndLabel(resourceAndLabel)
        val resourceId = resourceDao.insertResource(resourceAndLabel.resource)
        with(resourceAndLabel.labelWithDetails) {
            labelDao.insertLabel(label.copy(resourceId = resourceId))
            imageDao.insertResourceImages(resourceId, images)
            resourceDao.insertResourceDetails(details.map { it.copy(resourceId = resourceId) })
            relatedLabelDao.insertRelatedLabels(
                relatedLabels.map { it.copy(resourceId = resourceId) }
            )
        }
        return resourceId
    }

    // ====================
    // Master
    // ====================

    @Transaction
    @Query("")
    suspend fun deleteResourceAndMaster(resourceAndMaster: LocalResourceAndMaster): Boolean =
        withContext(Dispatchers.IO) {
            resourceDao.getResourceByMasterId(
                resourceAndMaster.masterWithDetails.master.masterId
            )?.let {
                resourceDao.deleteResource(it)
                imageDao.cleanImages()
                true
            } ?: false
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndMaster(resourceAndMaster: LocalResourceAndMaster): Long =
        resourceDao.insertResource(resourceAndMaster.resource).also { resourceId ->
            TODO("")
        }

    // ====================
    // Release
    // ====================

    @Transaction
    @Query("")
    suspend fun deleteResourceAndRelease(resourceAndRelease: LocalResourceAndRelease): Boolean =
        withContext(Dispatchers.IO) {
            resourceDao.getResourceByReleaseId(
                resourceAndRelease.releaseWithDetails.release.releaseId
            )?.let {
                resourceDao.deleteResource(it)
                imageDao.cleanImages()
                true
            } ?: false
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndRelease(resourceAndRelease: LocalResourceAndRelease): Long =
        resourceDao.insertResource(resourceAndRelease.resource).also { resourceId ->
            TODO("")
        }

    // ====================
    // Format
    // ====================

    @Transaction
    @Query("")
    suspend fun insertFormatsWithDetails(
        formatsWithDetails: List<LocalFormatWithDetails>
    ): List<Long> = formatDao.insertFormats(formatsWithDetails.map { it.format }).apply {
        zip(formatsWithDetails).forEach { (formatId, formatWithDetails) ->
            formatDao.insertFormatDetails(
                formatWithDetails.details.map { it.copy(formatId = formatId) }
            )
        }
    }

    // ====================
    // Track
    // ====================

    @Transaction
    @Query("")
    suspend fun insertTracksWithDetails(
        tracksWithDetails: List<LocalTrackWithDetails>
    ): List<Long> = trackDao.insertTracks(tracksWithDetails.map { it.track }).apply {
        zip(tracksWithDetails).forEach { (trackId, trackWithDetails) ->
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
