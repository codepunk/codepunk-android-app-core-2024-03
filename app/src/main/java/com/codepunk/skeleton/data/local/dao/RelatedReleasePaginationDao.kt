package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePagination

@Suppress("SpellCheckingInspection")
@Dao
interface RelatedReleasePaginationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaginations(paginations: List<LocalRelatedReleasePagination>)

    @Query("""
        SELECT * 
        FROM related_release_pagination 
        WHERE related_release_id = :relatedReleaseId
    """)
    suspend fun getPagination(relatedReleaseId: Long): LocalRelatedReleasePagination?

    @Query("DELETE FROM related_release_pagination WHERE related_release_id = :relatedReleaseId")
    suspend fun clearPagination(relatedReleaseId: Long)

    @Query("""
        SELECT created_at 
        FROM related_release_pagination 
        ORDER BY created_at DESC 
        LIMIT 1
    """)
    suspend fun getCreationTime(): Long?

}
