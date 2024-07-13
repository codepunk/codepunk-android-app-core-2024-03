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

    /*
     * Note that when deleting tracks, we should also delete credits
     * contained within the tracks.
     */
    @Query("""
      DELETE
      FROM track
      WHERE EXISTS (
         SELECT 1
           FROM resource_track_cross_ref
          WHERE resource_track_cross_ref.track_id = track.track_id
            AND resource_track_cross_ref.resource_id = :resourceId
      )
    """)
    abstract suspend fun deleteResourceTracks(resourceId: Long): Int

    // endregion Methods

}
