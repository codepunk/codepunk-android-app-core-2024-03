package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.codepunk.skeleton.data.local.entity.LocalTrack
import com.codepunk.skeleton.data.local.relation.LocalResourceTrackCrossRef

@Dao
abstract class TrackDao {

    // region Methods

    @Insert
    abstract suspend fun insertTracks(tracks: List<LocalTrack>): List<Long>

    @Insert
    abstract suspend fun insertResourceTrackCrossRefs(
        crossRefs: List<LocalResourceTrackCrossRef>
    ): List<Long>

    // endregion Methods

}
