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
    ): List<Long>

    @Insert
    abstract suspend fun insertTrackCreditCrossRefs(
        crossRefs: List<LocalTrackCreditCrossRef>
    ): List<Long>

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
         WHERE NOT EXISTS (
               SELECT resource_credit_cross_ref.resource_id
                 FROM resource_credit_cross_ref
                WHERE credit.credit_id = resource_credit_cross_ref.credit_id
         )
         AND NOT EXISTS (
               SELECT track_credit_cross_ref.track_id
                 FROM track_credit_cross_ref
                WHERE credit.credit_id = track_credit_cross_ref.credit_id
         )
    """)
    abstract suspend fun scrubCredits()

    // endregion Methods

}
