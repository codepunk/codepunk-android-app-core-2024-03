package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.entity.LocalImage
import com.codepunk.skeleton.data.local.relation.LocalArtistImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArtistDao {

    @Upsert
    abstract suspend fun upsertArtist(artist: LocalArtist): Long

    @Upsert
    abstract suspend fun upsertArtists(artists: List<LocalArtist>): List<Long>

    @Upsert
    abstract suspend fun upsertArtistImage(image: LocalImage): Long

    @Upsert
    abstract suspend fun upsertArtistImages(images: List<LocalImage>): List<Long>

    @Upsert
    abstract suspend fun upsertArtistImageCrossRef(crossRef: LocalArtistImageCrossRef)

    @Upsert
    abstract suspend fun upsertArtistImageCrossRefs(crossRefs: List<LocalArtistImageCrossRef>)

    @Upsert
    abstract suspend fun upsertArtistDetail(detail: LocalArtistDetail)

    @Upsert
    abstract suspend fun upsertArtistDetails(details: List<LocalArtistDetail>)

    @Upsert
    abstract suspend fun upsertArtistRelationship(relationship: LocalArtistRelationship)

    @Upsert
    abstract suspend fun upsertArtistRelationships(relationships: List<LocalArtistRelationship>)

    @Transaction
    @Upsert
    suspend fun upsertArtistWithDetails(artistWithDetails: LocalArtistWithDetails): Long {
        val artistId = upsertArtist(artistWithDetails.artist)
        val crossRefs = upsertArtistImages(artistWithDetails.images)
            .map { imageId -> LocalArtistImageCrossRef(artistId = artistId, imageId = imageId) }
        upsertArtistImageCrossRefs(crossRefs)
        upsertArtistDetails(artistWithDetails.details)
        upsertArtistRelationships(artistWithDetails.relationships)
        return artistId
    }

    @Transaction
    @Query("SELECT * FROM artist WHERE id = :id")
    abstract fun getArtistWithDetails(id: Long): Flow<LocalArtistWithDetails?>

}