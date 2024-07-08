package com.codepunk.skeleton.data.localv2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.codepunk.skeleton.data.localv2.entity.LocalArtist
import com.codepunk.skeleton.data.localv2.entity.LocalArtistReference
import com.codepunk.skeleton.data.localv2.entity.LocalImage
import com.codepunk.skeleton.data.localv2.entity.LocalLabel
import com.codepunk.skeleton.data.localv2.entity.LocalLabelReference
import com.codepunk.skeleton.data.localv2.entity.LocalResource
import com.codepunk.skeleton.data.localv2.entity.LocalResourceDetail
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndArtist
import com.codepunk.skeleton.data.localv2.relation.LocalArtistArtistReferenceCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalLabelLabelReferenceCrossRef
import com.codepunk.skeleton.data.localv2.relation.LocalResourceAndLabel
import com.codepunk.skeleton.data.localv2.relation.LocalResourceImageCrossRef
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

    // endregion Methods

}