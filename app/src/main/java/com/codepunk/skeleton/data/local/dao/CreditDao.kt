package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalCredit
import com.codepunk.skeleton.data.local.relation.LocalResourceCreditCrossRef
import com.codepunk.skeleton.data.local.relation.LocalTrackCreditCrossRef

@Dao
abstract class CreditDao {

    // region abstract Methods

    @Insert
    abstract suspend fun insertCredits(credits: List<LocalCredit>): List<Long>

    @Insert
    abstract suspend fun insertResourceCreditCrossRefs(
        crossRefs: List<LocalResourceCreditCrossRef>
    )

    @Insert
    abstract suspend fun insertTrackCreditCrossRefs(
        crossRefs: List<LocalTrackCreditCrossRef>
    )

    // endregion abstract Methods

    // region Methods

    @Transaction
    @Query("")
    suspend fun insertResourceCredits(
        resourceId: Long,
        credits: List<LocalCredit>
    ): List<Long> = insertCredits(credits).apply {
        filter { it != -1L }
            .map { LocalResourceCreditCrossRef(resourceId, it) }
            .run { insertResourceCreditCrossRefs(this) }
    }

    @Transaction
    @Query("")
    suspend fun insertTrackCredits(
        trackId: Long,
        credits: List<LocalCredit>
    ): List<Long> = insertCredits(credits).apply {
        filter { it != -1L }
            .map { LocalTrackCreditCrossRef(trackId, it) }
            .run { insertTrackCreditCrossRefs(this) }
    }

    @Query("""
      DELETE
      FROM credit
      WHERE EXISTS (
         SELECT 1
           FROM resource_credit_cross_ref
          WHERE resource_credit_cross_ref.credit_id = credit.credit_id
            AND resource_credit_cross_ref.resource_id = :resourceId
      )
    """)
    abstract suspend fun deleteResourceCredits(resourceId: Long): Int

    @Query("""
      DELETE 
      FROM credit
      WHERE EXISTS (
          SELECT 1
          FROM track_credit_cross_ref
          WHERE track_credit_cross_ref.track_id IN (
              SELECT track.track_id
              FROM track
              WHERE EXISTS (
                  SELECT 1
                  FROM resource_track_cross_ref
                  WHERE resource_track_cross_ref.track_id = track.track_id
                  AND resource_track_cross_ref.resource_id = :resourceId
              )
          )
          AND track_credit_cross_ref.credit_id = credit.credit_id
      )
    """)
    abstract suspend fun deleteTrackCreditsByResource(resourceId: Long): Int

    // endregion Methods

}
