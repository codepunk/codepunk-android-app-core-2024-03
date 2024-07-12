package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codepunk.skeleton.data.local.entity.LocalRelatedArtist

@Dao
abstract class RelatedArtistDao {

    // region Methods

    @Insert
    abstract suspend fun insertRelatedArtists(
        relatedArtists: List<LocalRelatedArtist>
    ): List<Long>

    @Query("")
    suspend fun insertRelatedArtists(
        resourceId: Long,
        relatedArtists: List<LocalRelatedArtist>
    ): List<Long> = insertRelatedArtists(relatedArtists.map { it.copy(resourceId = resourceId) })

    // endregion Methods

}
