package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.entity.LocalArtistDetail
import com.codepunk.skeleton.data.local.entity.LocalArtistRelationship
import com.codepunk.skeleton.data.local.relation.LocalArtistAliasCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistGroupCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistImageCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistMemberCrossRef
import com.codepunk.skeleton.data.local.relation.LocalArtistWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArtistDao {

    /*
     * TODO
     * Using OnConflictStrategy.IGNORE stops errors but will not work in the
     * future if I include things that actually change (like "stats" in releases)
     * In this case we can think about OnConflictStrategy.REPLACE
     * (or even @Update or @Upsert)
     */

    // region Methods

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtist(artist: LocalArtist): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtists(artists: List<LocalArtist>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistImageCrossRef(crossRef: LocalArtistImageCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistImageCrossRefs(crossRefs: List<LocalArtistImageCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistDetail(detail: LocalArtistDetail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistDetails(details: List<LocalArtistDetail>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistRelationship(relationship: LocalArtistRelationship): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistRelationships(
        relationships: List<LocalArtistRelationship>
    ): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistAliasCrossRefs(crossRefs: List<LocalArtistAliasCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistGroupCrossRefs(crossRefs: List<LocalArtistGroupCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertArtistMemberCrossRefs(crossRefs: List<LocalArtistMemberCrossRef>)

    @Transaction
    @Query("SELECT * FROM artist WHERE artist_id = :artistId")
    abstract fun getArtistWithDetails(artistId: Long): Flow<LocalArtistWithDetails?>

    open suspend fun insertArtistWithDetails(
        artistWithDetails: LocalArtistWithDetails
    ): Long = throw IllegalStateException(
        "This method can only be called from a descendant of this class"
    )

    // endregion Methods

}
