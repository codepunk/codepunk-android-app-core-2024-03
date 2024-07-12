package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.codepunk.skeleton.data.local.entity.LocalFormat
import com.codepunk.skeleton.data.local.entity.LocalFormatDetail

@Dao
interface FormatDao {

    // region Methods

    @Insert
    abstract suspend fun insertFormats(formats: List<LocalFormat>): List<Long>

    @Insert
    abstract suspend fun insertFormatDetails(details: List<LocalFormatDetail>)

    // endregion Methods

}
