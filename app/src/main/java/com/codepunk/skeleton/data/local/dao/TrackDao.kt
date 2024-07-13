package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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
    
    @Query("""
        DELETE 
          FROM track
         WHERE NOT EXISTS (
               SELECT resource_track_cross_ref.track_id
                 FROM resource_track_cross_ref
                WHERE track.track_id = resource_track_cross_ref.track_id
         )
    """)
    abstract suspend fun scrubTracks(): Int

    // endregion Methods

}
