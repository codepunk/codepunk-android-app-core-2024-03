package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.codepunk.skeleton.data.local.entity.LocalArtist

@Dao
interface ArtistDao {

    // region Methods

    @Upsert
    abstract suspend fun upsertArtist(artist: LocalArtist): Long

    // TODO SCOTT Query methods for LocalArtist !!

    // endregion Methods

}
