package com.codepunk.skeleton.data.remote.webservice

import arrow.core.Either
import arrow.core.Ior
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.codepunk.skeleton.data.remote.entity.RemoteArtist
import com.codepunk.skeleton.data.remote.entity.RemoteLabel
import com.codepunk.skeleton.data.remote.entity.RemoteMaster
import com.codepunk.skeleton.data.remote.entity.RemoteRelease
import com.codepunk.skeleton.data.remote.entity.RemoteReleasesByArtist
import com.codepunk.skeleton.data.remote.entity.RemoteSearchResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscogsWebservice {

    // region Methods

    @GET("artists/{artistId}")
    suspend fun getArtist(
        @Path("artistId") artistId: Long
    ): Either<CallError, RemoteArtist>

    @GET("artists/{artistId}/releases")
    suspend fun getReleasesByArtist(
        @Path("artistId") artistId: Long,
        @Query("sort") sort: String,
        @Query("sort_order") sortOrder: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Either<CallError, RemoteReleasesByArtist>

    @GET("labels/{labelId}")
    suspend fun getLabel(
        @Path("labelId") labelId: Long
    ): Either<CallError, RemoteLabel>

    @GET("masters/{masterId}")
    suspend fun getMaster(
        @Path("masterId") masterId: Long
    ): Either<CallError, RemoteMaster>

    @GET("releases/{releaseId}")
    suspend fun getRelease(
        @Path("releaseId") releaseId: Long
    ): Either<CallError, RemoteRelease>

    @GET("database/search")
    suspend fun search(
        @Query("q") query: String = "",
        @Query("type") type: String? = null,
        @Query("title") title: String? = null,
        @Query("release_title") releaseTitle: String? = null,
        @Query("credit") credit: String? = null,
        @Query("artist") artist: String? = null,
        @Query("anv") anv: String? = null,
        @Query("label") label: String? = null,
        @Query("genre") genre: String? = null,
        @Query("style") style: String? = null,
        @Query("country") country: String? = null,
        @Query("year") year: Int? = null,
        @Query("format") format: String? = null,
        @Suppress("SpellCheckingInspection")
        @Query("catno") catNo: String? = null,
        @Query("barcode") barcode: String? = null,
        @Query("track") track: String? = null,
        @Query("page") page: Int? = 0,
        @Query("per_page") perPage: Int? = 0
    ): Either<CallError, RemoteSearchResults>

    // endregion Methods

}