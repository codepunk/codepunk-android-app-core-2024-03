package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.codepunk.skeleton.data.local.entity.LocalIdentifier

@Dao
interface IdentifierDao {

    // region Methods

    @Insert
    abstract suspend fun insertIdentifiers(identifiers: List<LocalIdentifier>): List<Long>

    // endregion Methods

}
