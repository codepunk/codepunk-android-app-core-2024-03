package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.skeleton.data.local.entity.LocalRelatedReleasePagination

@Dao
interface RelatedReleasePaginationDao {

    @Suppress("SpellCheckingInspection")
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaginations(paginationList: List<LocalRelatedReleasePagination>)

    @Query("""
        SELECT * 
        FROM related_release_pagination 
        WHERE related_release_id = :relatedReleaseId
    """)
    suspend fun getPagination(relatedReleaseId: Long): LocalRelatedReleasePagination?

    @Query("""
        SELECT created_at 
        FROM related_release_pagination 
        ORDER BY created_at DESC 
        LIMIT 1
    """)
    suspend fun getCreationTime(): Long?

}
