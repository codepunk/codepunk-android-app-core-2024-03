package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDetail

@Dao
abstract class FormatDao {

    // region Methods

    @Insert
    abstract suspend fun insertFormats(formats: List<LocalFormat>): List<Long>

    @Insert
    abstract suspend fun insertFormatDetails(details: List<LocalFormatDetail>): List<Long>

    @Query("")
    suspend fun insertFormatDetails(formatId: Long, details: List<LocalFormatDetail>): List<Long> =
        insertFormatDetails(details.map { it.copy(formatId = formatId) })

    // endregion Methods

}
