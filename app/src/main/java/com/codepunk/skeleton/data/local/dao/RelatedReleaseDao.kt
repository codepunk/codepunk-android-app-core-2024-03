package com.codepunk.skeleton.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.codepunk.skeleton.data.local.entity.LocalRelatedRelease

@Dao
abstract class RelatedReleaseDao {

    // region Methods

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRelatedReleases(
        relatedReleases: List<LocalRelatedRelease>
    ): List<Long>

    @Query("")
    suspend fun insertRelatedReleases(
        resourceId: Long,
        relatedReleases: List<LocalRelatedRelease>
    ): List<Long> = insertRelatedReleases(relatedReleases.map { it.copy(resourceId = resourceId) })

    @Query("DELETE FROM related_release WHERE resource_id = :resourceId")
    abstract suspend fun clearRelatedReleases(resourceId: Long)

    @RawQuery(observedEntities = [LocalRelatedRelease::class])
    abstract fun getReleasesByResource(
        query: SupportSQLiteQuery
    ): PagingSource<Int, LocalRelatedRelease>

    /*
     * TODO PAGING This needs the incorporate the concept of page somehow
     */
    @Query("")
    fun getReleasesByArtist(
        artistId: Long,
        sort: String,
        ascending: Boolean
    ): PagingSource<Int, LocalRelatedRelease> {
        val query = SimpleSQLiteQuery(
            """
                SELECT related_release.*
                FROM related_release
                LEFT OUTER JOIN artist
                ON related_release.resource_id = artist.resource_id
                WHERE artist.artist_id = $artistId
                ORDER BY $sort ${if (ascending) " ASC" else " DESC"}
            """
        )
        return getReleasesByResource(query)
    }

    // endregion Methods

}
