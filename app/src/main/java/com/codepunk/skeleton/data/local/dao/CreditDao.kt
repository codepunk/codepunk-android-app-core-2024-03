package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.codepunk.skeleton.data.local.entity.LocalCredit

@Dao
interface CreditDao {

    // region Methods

    @Suppress("Unused")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCredit(credit: LocalCredit): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCredits(credits: List<LocalCredit>): List<Long>

    // endregion Methods

}
