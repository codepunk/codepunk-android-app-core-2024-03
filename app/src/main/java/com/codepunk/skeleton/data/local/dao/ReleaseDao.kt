package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.codepunk.skeleton.data.local.entity.ReleaseFormat
import com.codepunk.skeleton.data.local.entity.ReleaseFormatDescription
import com.codepunk.skeleton.data.local.relation.ReleaseFormatWithDescriptions

@Dao
interface ReleaseDao {

    // region Methods

    @Upsert
    suspend fun upsertReleaseFormat(format: ReleaseFormat): Long

    @Upsert
    suspend fun upsertReleaseFormatDescription(description: ReleaseFormatDescription)

    @Transaction
    suspend fun upsertReleaseFormatWithDescriptions(
        formatWithDescriptions: ReleaseFormatWithDescriptions
    ) {
        val formatId = upsertReleaseFormat(formatWithDescriptions.releaseFormat)
        formatWithDescriptions.descriptions.forEach {
            upsertReleaseFormatDescription(
                it.copy(formatId = formatId)
            )
        }
    }

    @Transaction
    suspend fun upsertReleaseFormatsWithDescriptions(
        formatsWithDescriptions: List<ReleaseFormatWithDescriptions>
    ) {
        formatsWithDescriptions.forEach {
            upsertReleaseFormatWithDescriptions(it)
        }
    }

    @Transaction
    @Query("SELECT * FROM release_format WHERE format_id = :formatId")
    suspend fun getReleaseFormatWithDescriptions(formatId: Long): List<ReleaseFormatWithDescriptions>

    // endregion Methods

}
