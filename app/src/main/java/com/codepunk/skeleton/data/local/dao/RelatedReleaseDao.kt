package com.codepunk.skeleton.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.codepunk.skeleton.data.local.entity.LocalRelatedRelease

@Dao
abstract class RelatedReleaseDao {

    // region Methods

    @Insert
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

    /* TODO Might not need this
    @Query("""
        DELETE
        FROM related_release
        WHERE EXISTS (
           SELECT 1
           FROM artist
           WHERE artist.resource_id = related_release.resource_id
           AND artist.artist_id = :artistId
      )
    """)
    abstract suspend fun clearRelatedReleasesByArtist(artistId: Long)
     */

    @RawQuery(observedEntities = [LocalRelatedRelease::class])
    abstract fun getReleasesByResource(
        query: SupportSQLiteQuery
    ): PagingSource<Int, LocalRelatedRelease>

    @Query("")
    fun getReleasesByResource(
        resourceId: Long,
        sort: String,
        ascending: Boolean
    ): PagingSource<Int, LocalRelatedRelease> {
        val query = SimpleSQLiteQuery(
            """
                SELECT *
                FROM related_release
                WHERE resource_id = $resourceId
                ORDER BY $sort ${if (ascending) " ASC" else " DESC"}
            """ //.trimIndent()
        )
        return getReleasesByResource(query)
    }

    val q = """
        SELECT resource.*
        FROM resource
        LEFT OUTER JOIN artist
        ON resource.resource_id = artist.resource_id
        WHERE artist.artist_id = :artistId
    """

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
            """ //.trimIndent()
        )
        return getReleasesByResource(query)
    }

    // endregion Methods

}
