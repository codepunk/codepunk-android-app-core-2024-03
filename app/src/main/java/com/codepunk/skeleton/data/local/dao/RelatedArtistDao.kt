package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.codepunk.skeleton.data.local.entity.LocalRelatedArtist

@Dao
interface RelatedArtistDao {

    // region Methods

    @Insert
    abstract suspend fun insertRelatedArtists(
        relatedArtists: List<LocalRelatedArtist>
    ): List<Long>

    // endregion Methods

}
