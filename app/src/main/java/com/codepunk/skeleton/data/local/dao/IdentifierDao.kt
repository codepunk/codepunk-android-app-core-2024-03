package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.codepunk.skeleton.data.local.entity.LocalIdentifier

@Dao
abstract class IdentifierDao {

    // region Methods

    @Insert
    abstract suspend fun insertIdentifiers(identifiers: List<LocalIdentifier>): List<Long>

    suspend fun insertIdentifiers(releaseId: Long,identifiers: List<LocalIdentifier>): List<Long> =
        insertIdentifiers(identifiers.map { it.copy(releaseId = releaseId) })

    // endregion Methods

}
