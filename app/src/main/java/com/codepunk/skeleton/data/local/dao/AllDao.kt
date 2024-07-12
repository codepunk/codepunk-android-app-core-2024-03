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

    @Transaction
    @Query("")
    suspend fun deleteResourceAndArtist(resourceAndArtist: LocalResourceAndArtist): Boolean =
        withContext(Dispatchers.IO) {
            val artistId = resourceAndArtist.artistWithDetails.artist.artistId
            val savedResource = resourceDao.getResourceByArtistId(artistId)
            savedResource?.let {
                resourceDao.deleteResource(it)
                imageDao.cleanImages()
                true
            } ?: false
        }

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

    @Transaction
    @Query("")
    suspend fun insertResourceAndArtist(resourceAndArtist: LocalResourceAndArtist): Long {
        // I think the easiest thing here is:
        // Just delete existing resource/artist (if it exists) and start over
        deleteResourceAndArtist(resourceAndArtist)

        // Upsert the resource
        val resourceId = resourceDao.insertResource(resourceAndArtist.resource)
        with(resourceAndArtist.artistWithDetails) {
            // Upsert the artist
            val artistId = artistDao.insertArtist(artist.copy(resourceId = resourceId))

            // Insert the various images, details, etc.
            val imageResults = imageDao.insertResourceImages(resourceId, images)
            val detailsResults = resourceDao.insertResourceDetails(
                details.map { it.copy(resourceId = resourceId) }
            )
            val relatedArtistResults = relatedArtistDao.insertRelatedArtists(
                relatedArtists.map { it.copy(resourceId = resourceId) }
            )
            val x = "$resourceId $artistId $imageResults $detailsResults $relatedArtistResults"
        }
        return resourceId
    }

    @Transaction
    @Query("")
    suspend fun insertResourceAndLabel(resourceAndLabel: LocalResourceAndLabel): Long =
        resourceDao.insertResource(resourceAndLabel.resource).also { resourceId ->
            TODO("")
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndMaster(resourceAndMaster: LocalResourceAndMaster): Long =
        resourceDao.insertResource(resourceAndMaster.resource).also { resourceId ->
            TODO("")
        }

    @Transaction
    @Query("")
    suspend fun insertResourceAndRelease(resourceAndRelease: LocalResourceAndRelease): Long =
        resourceDao.insertResource(resourceAndRelease.resource).also { resourceId ->
            TODO("")
        }

    // endregion Methods

}
