package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.codepunk.skeleton.data.local.entity.LocalTrack

@Dao
interface TrackDao {

    // region Methods

    @Suppress("Unused")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrack(track: LocalTrack): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTracks(tracks: List<LocalTrack>): List<Long>

    // endregion Methods

}
