package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
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

    suspend fun insertResourceCredits(
        resourceId: Long,
        credits: List<LocalCredit>
    ): List<Long> = insertCredits(credits).apply {
        filter { it != -1L }
            .map { LocalResourceCreditCrossRef(resourceId, it) }
            .run { insertResourceCreditCrossRefs(this) }
    }

    suspend fun insertTrackCredits(
        trackId: Long,
        credits: List<LocalCredit>
    ): List<Long> = insertCredits(credits).apply {
        filter { it != -1L }
            .map { LocalTrackCreditCrossRef(trackId, it) }
            .run { insertTrackCreditCrossRefs(this) }
    }

    // endregion Methods

}
