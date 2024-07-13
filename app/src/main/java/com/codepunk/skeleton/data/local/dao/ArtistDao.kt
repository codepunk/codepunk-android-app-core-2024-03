package com.codepunk.skeleton.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.skeleton.data.local.entity.LocalArtist
import com.codepunk.skeleton.data.local.relation.LocalResourceAndArtist
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    // region Methods

    @Insert
    suspend fun insertArtist(artist: LocalArtist): Long

    @Transaction
    @Query("""
        SELECT resource.*
          FROM resource
          LEFT OUTER JOIN artist
            ON resource.resource_id = artist.resource_id
         WHERE artist.artist_id = :artistId
    """)
    fun getResourceAndArtist(artistId: Long): Flow<LocalResourceAndArtist?>

    // endregion Methods

}
