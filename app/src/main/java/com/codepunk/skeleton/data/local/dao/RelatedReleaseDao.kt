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

    // endregion Methods

}
